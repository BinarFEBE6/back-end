package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.binaracademy.finalproject.repositories.ScheduleRepo;
import org.binaracademy.finalproject.services.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduleServiceTest {

    @MockBean
    private ScheduleRepo scheduleRepo;
    @Autowired
    private ScheduleService scheduleService;

    ScheduleEntity scheduleEntity1;
    ScheduleEntity scheduleEntity2;
    List<ScheduleEntity> scheduleEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        scheduleEntity1 = new ScheduleEntity(
                1L, "Jakarta", "Bali",
                new BigDecimal(500000), 100, LocalDate.now(),
                1L, 1L, 1L, null, null,
                null, null, null);
        scheduleEntityList.add(scheduleEntity1);
    }

    @Test
    @DisplayName("Create Schedule")
    void create() {
        when(scheduleRepo.save(scheduleEntity1)).thenReturn(scheduleEntity1);
        assertEquals(scheduleEntity1.getDepartureAiport(), scheduleService.create(scheduleEntity1).getDepartureAiport());
        verify(scheduleRepo).save(scheduleEntity1);
    }

    @Test
    @DisplayName("Get All Schedule")
    void getAll() {
        when(scheduleRepo.findAll()).thenReturn(scheduleEntityList);
        assertNotNull(scheduleService.getAll());
        verify(scheduleRepo).findAll();
    }

    @Test
    @DisplayName("Get One Schedule")
    void getOneSchedule() {
        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(scheduleEntity1));
        assertNotNull(scheduleService.getOneSchedule(1L));
        verify(scheduleRepo).findById(1L);
    }

    @Test
    @DisplayName("Get All Schedule From To")
    void getFromTo() {
        when(scheduleRepo.findScheduleFromTo(
                "Jakarta", "Bali", LocalDate.now())).thenReturn(scheduleEntityList);
        assertNotNull(scheduleService.getFromTo("Jakarta", "Bali"));
        verify(scheduleRepo).findScheduleFromTo("Jakarta", "Bali", LocalDate.now());
    }

    @Test
    @DisplayName("Get All Page Schedule From To")
    void getPageFromTo() {
        Pageable pageable = PageRequest.of(1, 2, Sort.by("schedule_id").descending());
        Iterable<ScheduleEntity> scheduleEntities = List.of(scheduleEntity1);
        Page<ScheduleEntity> page = new Page<ScheduleEntity>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super ScheduleEntity, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<ScheduleEntity> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<ScheduleEntity> iterator() {
                return null;
            }
        };
        when(scheduleRepo.findScheduleFromToPage(
                "Jakarta", "Bali", LocalDate.now(), pageable)).thenReturn(page);
        assertNotNull(scheduleService.getPageFromTo("Jakarta", "Bali", pageable));
        verify(scheduleRepo).findScheduleFromToPage("Jakarta", "Bali", LocalDate.now(), pageable);
    }
}