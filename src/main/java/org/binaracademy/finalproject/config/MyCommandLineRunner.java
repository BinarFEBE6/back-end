package org.binaracademy.finalproject.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.CountryData;
import org.binaracademy.finalproject.data.ScheduleTimeData;
import org.binaracademy.finalproject.data.SeatData;
import org.binaracademy.finalproject.data.TimeData;
import org.binaracademy.finalproject.entity.*;
import org.binaracademy.finalproject.repositories.RoleRepo;
import org.binaracademy.finalproject.services.CategoryClassService;
import org.binaracademy.finalproject.services.CityService;
import org.binaracademy.finalproject.services.CountryService;
import org.binaracademy.finalproject.services.ScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.binaracademy.finalproject.services.AirportService;
import org.binaracademy.finalproject.services.PesawatService;
import org.binaracademy.finalproject.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private final CityService cityService;

    @Autowired
    private final CountryService countryService;

    @Autowired
    private final ScheduleTimeService scheTimeService;

    @Autowired
    private final CategoryClassService categoryService;

    @Autowired
    private final AirportService airportService;

    @Autowired
    private final PesawatService pesawatService;

    @Autowired
    private final RoleRepo roleRepo;
    private final SeatService seatService;


    @Override
    public void run(String... args) throws Exception {
        if (countryService.getAll().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<CountryData>> typeReference = new TypeReference<List<CountryData>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/countryandcity.json");
            try {
                List<CountryData> country = mapper.readValue(inputStream, typeReference);
                country.forEach(country1 -> {
                    countryService.create(new CountryEntity(country1.getId(), country1.getName(), LocalDateTime.now(), null));
                    country1.getCity().forEach(cityData -> {
                        CityEntity city = cityService.create(new CityEntity(null, cityData.getName(), LocalDateTime.now(), null, country1.getId(), null));
                        AirportEntity airport = airportService.create(new AirportEntity(null, cityData.getAirport().getName(),
                                city.getId(), LocalDateTime.now(), null, null));
                        cityData.getAirport().getPesawat().forEach(pesawatData ->
                                pesawatService.create(new PesawatEntity(null, pesawatData.getName(), airport.getId(), null, null, null)));
                    });
                });
            }catch (IOException e){
                log.info("Unable to save country : {}", e.getMessage());
            }
        }

        if(scheTimeService.getAll().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<ScheduleTimeData>> typeReference = new TypeReference<List<ScheduleTimeData>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/scheduleTime.json");

            try {
                List<ScheduleTimeData> scheduleTime = mapper.readValue(inputStream, typeReference);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
                scheduleTime.forEach(schedule -> {
                    List<TimeData> schTime = schedule.getScheduleTime();

                    schTime.forEach(time ->
                            scheTimeService.create(new ScheduleTimeEntity(time.getId(), schedule.getDay(), LocalTime.parse(time.getStart_time(), format), LocalTime.parse(time.getEnd_time(), format), LocalDateTime.now(), null)));
                });
            }catch (IOException e){
                log.info("Unable to save Schedule Time : {}", e);
            }
        }

        if(categoryService.getAll().isEmpty()) {
            String[] ticketCategory = {"Economy", "Bussiness", "Executive"};
            for(Integer i = 0 ; i<3 ; i++){
                Long longI = Long.valueOf(i);
                categoryService.create(new CategoryClassEntity(longI,ticketCategory[i],LocalDateTime.now(),null));
            }
        }

        if(roleRepo.findAll().isEmpty()) {
            String[] roles = {"ROLE_ADMIN", "ROLE_USER"};
            IntStream.range(0, roles.length).forEach(x -> {
                roleRepo.save(new RoleEntity(ERole.valueOf(roles[x])));
                log.info("Role has been created : {}", roles[x]);
            });
        }

        if (seatService.getAll().isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<SeatData>> typeReference = new TypeReference<List<SeatData>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/seats.json");
            try {
                List<SeatData> seat = mapper.readValue(inputStream, typeReference);
                seat.forEach(seatData -> seatService.create(new SeatEntity(null, seatData.getName())));
            }catch (IOException e){
                log.info("Unable to save country : {}", e.getMessage());
            }
        }
    }

}
