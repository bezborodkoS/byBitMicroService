package com.example.bybitproject;

import com.example.bybitproject.model.LaunchPoolDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ParserLaunchPool {

    public List<LaunchPoolDTO> parsingLaunchPool(String listPoolInString){
        return parseLaunchPoolInfo(listPoolInString);
    }

//    Преобразуем строку LaunchPools в List обектов LaunchPoolDTO
    private List<LaunchPoolDTO> parseLaunchPoolInfo(String listPoolInString) {
        List<LaunchPoolDTO> launchPools = new ArrayList<>();

        // Регулярное выражение для извлечения каждого блока LaunchPoolInfo
        Pattern pattern = Pattern.compile(
                "LaunchPoolInfo\\{exchange='(.*?)', launchPool='(.*?)', pools=\\{(.*?)\\}, period='(.*?)', status='(.*?)'\\}");
        Matcher matcher = pattern.matcher(listPoolInString);

        // Проходим по всем совпадениям
        while (matcher.find()) {
            String exchange = matcher.group(1);
            String launchPool = matcher.group(2);
            String poolsStr = matcher.group(3);
            String period = matcher.group(4);
            String status = matcher.group(5);

            // Разбор pool-значений
            Map<String, String> pools = new HashMap<>();
//            Разбивает строку pools на масив подстрок и добавляем в Хеш Карту
            String[] poolEntries = poolsStr.split(", ");
            for (String entry : poolEntries) {
//                Конечная разбивка подстроки в конкретные ключ-значения
                String[] keyValue = entry.split(":=");
                if (keyValue.length == 2) {
                    pools.put(keyValue[0], keyValue[1]);
                }
            }

            // Создание объекта LaunchPoolInfo
            launchPools.add(new LaunchPoolDTO(exchange, launchPool, pools, period, status));
        }

        return launchPools;
    }


}
