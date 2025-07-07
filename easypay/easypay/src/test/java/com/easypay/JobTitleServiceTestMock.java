package com.easypay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.JobTitle;
import com.easypay.repository.JobTitleRepository;
import com.easypay.service.JobTitleService;

@SpringBootTest
public class JobTitleServiceTestMock {

    @InjectMocks
    private JobTitleService jobTitleService;

    @Mock
    private JobTitleRepository jobTitleRepository;

    private JobTitle jobTitle;

    @BeforeEach
    public void setup() {
        jobTitle = new JobTitle();
        jobTitle.setId(1);
        jobTitle.setTitleName("Software Engineer");
        jobTitle.setBasicSalary(50000);
        jobTitle.setHraRate(20);
        jobTitle.setDaRate(10);
        jobTitle.setOtherAllowances(5000);
        System.out.println("JobTitle created: " + jobTitle);
    }

    @Test
    public void testAddJobTitle() {
        when(jobTitleRepository.save(jobTitle)).thenReturn(jobTitle);
        assertEquals(jobTitle, jobTitleService.addJobTitle(jobTitle));
    }

    @Test
    public void testGetAllJobTitles() {
        when(jobTitleRepository.findAll()).thenReturn(List.of(jobTitle));
        List<JobTitle> list = jobTitleService.getAllJobTitles();
        assertEquals(1, list.size());
        assertEquals("Software Engineer", list.get(0).getTitleName());
    }

    @Test
    public void testGetAllJobTitles_EmptyList() {
        when(jobTitleRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(jobTitleService.getAllJobTitles().isEmpty());
    }

    @Test
    public void testGetJobtitleById_Valid() {
        when(jobTitleRepository.findById(1)).thenReturn(Optional.of(jobTitle));
        assertEquals(jobTitle, jobTitleService.getJobtitle(1));
    }

    @Test
    public void testGetJobtitleById_Invalid() {
        when(jobTitleRepository.findById(999)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> jobTitleService.getJobtitle(999));
        assertEquals("Id given is invalid".toLowerCase(), ex.getMessage().toLowerCase());
    }

    @Test
    public void testUpdateJobTitle_Valid() {
        JobTitle updated = new JobTitle();
        updated.setTitleName("Senior Developer");
        updated.setBasicSalary(80000);
        updated.setHraRate(25);
        updated.setDaRate(12);
        updated.setOtherAllowances(10000);

        when(jobTitleRepository.findById(1)).thenReturn(Optional.of(jobTitle));
        when(jobTitleRepository.save(any(JobTitle.class))).thenReturn(jobTitle);

        JobTitle result = jobTitleService.updateJobTitle(1, updated);

        assertEquals("Senior Developer", result.getTitleName());
        assertEquals(80000, result.getBasicSalary());
        assertEquals(25, result.getHraRate());
        assertEquals(12, result.getDaRate());
        assertEquals(10000, result.getOtherAllowances());
    }

    @Test
    public void testUpdateJobTitle_PartialUpdate() {
        JobTitle partial = new JobTitle();
        partial.setTitleName("Lead");

        when(jobTitleRepository.findById(1)).thenReturn(Optional.of(jobTitle));
        when(jobTitleRepository.save(any(JobTitle.class))).thenReturn(jobTitle);

        JobTitle result = jobTitleService.updateJobTitle(1, partial);

        assertEquals("Lead", result.getTitleName());
        assertEquals(50000, result.getBasicSalary()); // unchanged
    }

    @Test
    public void testUpdateJobTitle_InvalidId() {
        when(jobTitleRepository.findById(999)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> jobTitleService.updateJobTitle(999, new JobTitle()));
        assertEquals("Benefit not found id given is invalid.".toLowerCase(), ex.getMessage().toLowerCase());
    }

    @Test
    public void testDeleteJobtitle_Valid() {
        when(jobTitleRepository.existsById(1)).thenReturn(true);
        doNothing().when(jobTitleRepository).deleteById(1);

        boolean deleted = jobTitleService.deleteJobtitle(1);
        assertTrue(deleted);
        verify(jobTitleRepository).deleteById(1);
    }

    @Test
    public void testDeleteJobtitle_Invalid() {
        when(jobTitleRepository.existsById(100)).thenReturn(false);

        boolean deleted = jobTitleService.deleteJobtitle(100);
        assertFalse(deleted);
        verify(jobTitleRepository, never()).deleteById(100);
    }

    @AfterEach
    public void clear() {
        jobTitle = null;
        System.out.println("JobTitle object cleared: " + jobTitle);
    }
}