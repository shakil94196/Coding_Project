package com.example.project.Coding_Project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryController.class)
public class BatteryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatteryService batteryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addBatteries_ShouldReturnSavedBatteries() throws Exception {
        List<Battery> batteries = Arrays.asList(
                new Battery("Battery1", "12345", 100),
                new Battery("Battery2", "23456", 200)
        );

        when(batteryService.saveAll(batteries)).thenReturn(batteries);

        mockMvc.perform(post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteries)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(batteries)));
    }

    @Test
    public void getBatteriesByPostcodeRange_ShouldReturnBatteriesAndStatistics() throws Exception {
        List<Battery> batteries = Arrays.asList(
                new Battery("Battery2", "23456", 200),
                new Battery("Battery3", "24567", 150)
        );

        when(batteryService.getBatteriesByPostcodeRange("20000", "30000")).thenReturn(batteries);

        mockMvc.perform(get("/batteries")
                        .param("postcodeRange", "20000-30000"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"batteryNames\": [\"Battery2\", \"Battery3\"], \"statistics\": { \"totalWattCapacity\": 350, \"averageWattCapacity\": 175 } }"));
    }
}
