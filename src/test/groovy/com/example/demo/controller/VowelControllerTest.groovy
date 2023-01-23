package com.example.demo.controller

import com.example.demo.service.VowelService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class VowelControllerTest extends Specification {
    private VowelService vowelService
    private MockMvc mockMvc

    void setup() {
        vowelService = Mock()
        def vowelController = new VowelController(vowelService)
        mockMvc = MockMvcBuilders.standaloneSetup(vowelController).build()
    }

    def "TestCalculateAverageNumberOfVowels"() {

        given:
        def url = "/v1/vowel/average"

        when:
        def result = mockMvc.perform(get(url)).andReturn()

        then:
        1 * vowelService.calculateAverageNumberOfVowels()

        def response = result.response
        response.status == HttpStatus.NO_CONTENT.value()
    }
}
