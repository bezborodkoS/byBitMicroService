package com.example.bybitproject.service;

import com.example.bybitproject.ParserLaunchPool;
import com.example.bybitproject.model.LaunchPool;
import com.example.bybitproject.model.LaunchPoolDTO;
import com.example.bybitproject.model.Pool;
import com.example.bybitproject.repository.LaunchPoolRepository;
import com.example.bybitproject.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaunchPoolService {

    public static final String START_SOON = "Скоро почнеться";
    public static final String Active = "Активний";
    private final ParserLaunchPool parserLaunchPool;
    private final LaunchPoolRepository launchPoolRepository;
    private final PoolRepository poolRepository;
    private final LaunchPoolDBMethodsService launchPoolDBMethodsService;

    public LaunchPoolService(LaunchPoolDBMethodsService launchPoolDBMethodsService, ParserLaunchPool parserLaunchPool, LaunchPoolRepository launchPoolRepository, PoolRepository poolRepository) {
        this.launchPoolDBMethodsService = launchPoolDBMethodsService;
        this.parserLaunchPool = parserLaunchPool;
        this.launchPoolRepository = launchPoolRepository;
        this.poolRepository = poolRepository;
    }

    public boolean saveLaunchPool(String str) {
        List<LaunchPoolDTO> launchPoolDTOList = parserLaunchPool.parsingLaunchPool(str);
        launchPoolDBMethodsService.saveLaunchPool(launchPoolDTOList);

        return true;
    }

    public List<LaunchPoolDTO> getLaunchPoolsActive(){
        return convertLaunchPoolsToLaunchPoolDTOS(launchPoolRepository.findAllByStatus(Active));
    }

    public List<LaunchPoolDTO> getLaunchPoolsStartSoon(){
        return convertLaunchPoolsToLaunchPoolDTOS(launchPoolRepository.findAllByStatus(START_SOON));
    }

    public void updateLaunchPoolsInDb(){
        updateActive();
        updateNoActive();
    }


    private void updateNoActive() {
//        List<LaunchPool> launchPoolDTOListActivity = launchPoolDTOListActivity();
        SimpleDateFormat objSDF = new SimpleDateFormat("dd.mm.yyyy hh:mm");


        List<LaunchPool> launchPoolDTOListNoActivity = launchPoolDTOListNoActivity();
        String startLaunchPool;
        String dateNowString = returnDateInNeedFormat(returnCurrentTimeInFormatLaunchPool());
        try {
            for (LaunchPool launchPool : launchPoolDTOListNoActivity) {
                startLaunchPool = launchPool.getPeriod().substring(0, 11);
                startLaunchPool = returnDateInNeedFormat(startLaunchPool);
                System.out.println(startLaunchPool + " pool in format start");
                System.out.println(dateNowString + " now");
                Date dateStart = objSDF.parse(startLaunchPool);
//                dateStart.setYear(year);
                Date dateNow = objSDF.parse(dateNowString);
//                dateNow.setYear(year);

                if (dateStart.compareTo(dateNow) < 0) {
                    System.out.println(objSDF.format(dateStart) + " раньше чем " + objSDF.format(dateNow));
                    launchPool.setStatus(Active);
                    launchPoolDBMethodsService.updateLaunchPool(launchPool);
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateActive() {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd.mm.yyyy hh:mm");


        List<LaunchPool> launchPoolDTOListNoActivity = launchPoolDTOListActivity();
        String endtLaunchPool;
        String dateNowString = returnDateInNeedFormat(returnCurrentTimeInFormatLaunchPool());
//        14
        try {
            for (LaunchPool launchPool : launchPoolDTOListNoActivity) {
                endtLaunchPool = launchPool.getPeriod().substring(14);
                endtLaunchPool = returnDateInNeedFormat(endtLaunchPool);
                System.out.println(endtLaunchPool + " pool in format end");
                System.out.println(dateNowString + " now");
                Date dateEnd = objSDF.parse(endtLaunchPool);
//                dateStart.setYear(year);
                Date dateNow = objSDF.parse(dateNowString);
//                dateNow.setYear(year);

                if (dateNow.compareTo(dateEnd) >0) {
                    System.out.println(objSDF.format(dateEnd) + " позже  чем " + objSDF.format(dateNow));
                    launchPoolDBMethodsService.deleteLaunchPool(launchPool);
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    //    Возращает текущюю дату в формате как приходит в LaunchPool_period.
    private String returnCurrentTimeInFormatLaunchPool() {
        String currentDateTime = String.valueOf(java.time.LocalDateTime.now()).replace("T", " ").replace("-", ".");
        String month = currentDateTime.substring(5, 7);
        String day = currentDateTime.substring(8, 10);
        String time = currentDateTime.substring(11, 16);
        currentDateTime = day + "." + month + " " + time;
        return currentDateTime;
    }


    private List<LaunchPool> launchPoolDTOListActivity() {
        return launchPoolRepository.findAll().stream().filter(launchPool -> launchPool.getStatus().equals("Активний")).collect(Collectors.toList());
    }

    private List<LaunchPool> launchPoolDTOListNoActivity() {
        return launchPoolRepository.findAll().stream().filter(launchPool -> launchPool.getStatus().equals("Скоро почнеться")).collect(Collectors.toList());
    }

    //    Вернуть дату в нужном формате который мы знаем в методе !!!update!!! в виде строки
    private String returnDateInNeedFormat(String date) {
        return date.substring(0, 5) + ".2024 " + date.substring(6);
    }


//    Преобразовывает launchPool в DTO
    private LaunchPoolDTO convertLaunchPoolToLaunchPoolDto(LaunchPool launchPool){
        List<Pool> poolList = poolRepository.findByLaunchPool_Id(launchPool.getId());
        Map<String,String> poolMap = new HashMap<>();
        for (Pool pool : poolList) {
//                Конечная разбивка подстроки в конкретные ключ-значения
                poolMap.put(pool.getTypePool(), pool.getPercentTypePool());
        }
        LaunchPoolDTO launchPoolDTO = new LaunchPoolDTO(launchPool.getExchange(), launchPool.getLaunchPool(),poolMap, launchPool.getPeriod(), launchPool.getStatus());
        return launchPoolDTO;
    }

//    Преобразовывает List<launchPool> в List<DTO>
    private List<LaunchPoolDTO> convertLaunchPoolsToLaunchPoolDTOS(List<LaunchPool> launchPoolList){
        List<LaunchPoolDTO> launchPoolDTOList = new ArrayList<>();
        for (LaunchPool launch : launchPoolList) {
            launchPoolDTOList.add(convertLaunchPoolToLaunchPoolDto(launch));
        }
        return launchPoolDTOList;
    }

}
