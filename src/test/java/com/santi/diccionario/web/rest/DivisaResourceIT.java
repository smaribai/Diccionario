package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.repository.DivisaRepository;
import com.santi.diccionario.service.criteria.DivisaCriteria;
import com.santi.diccionario.service.dto.DivisaDTO;
import com.santi.diccionario.service.mapper.DivisaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DivisaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DivisaResourceIT {

    private static final String DEFAULT_CODIGO_DIVISA = "AAA";
    private static final String UPDATED_CODIGO_DIVISA = "BBB";

    private static final String DEFAULT_NOMBRE_DIVISA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DIVISA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/divisas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DivisaRepository divisaRepository;

    @Autowired
    private DivisaMapper divisaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisaMockMvc;

    private Divisa divisa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisa createEntity(EntityManager em) {
        Divisa divisa = new Divisa().codigoDivisa(DEFAULT_CODIGO_DIVISA).nombreDivisa(DEFAULT_NOMBRE_DIVISA);
        return divisa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisa createUpdatedEntity(EntityManager em) {
        Divisa divisa = new Divisa().codigoDivisa(UPDATED_CODIGO_DIVISA).nombreDivisa(UPDATED_NOMBRE_DIVISA);
        return divisa;
    }

    @BeforeEach
    public void initTest() {
        divisa = createEntity(em);
    }

    @Test
    @Transactional
    void createDivisa() throws Exception {
        int databaseSizeBeforeCreate = divisaRepository.findAll().size();
        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);
        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isCreated());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeCreate + 1);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getCodigoDivisa()).isEqualTo(DEFAULT_CODIGO_DIVISA);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(DEFAULT_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void createDivisaWithExistingId() throws Exception {
        // Create the Divisa with an existing ID
        divisa.setId(1L);
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        int databaseSizeBeforeCreate = divisaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoDivisaIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisaRepository.findAll().size();
        // set the field null
        divisa.setCodigoDivisa(null);

        // Create the Divisa, which fails.
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isBadRequest());

        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreDivisaIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisaRepository.findAll().size();
        // set the field null
        divisa.setNombreDivisa(null);

        // Create the Divisa, which fails.
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isBadRequest());

        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDivisas() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoDivisa").value(hasItem(DEFAULT_CODIGO_DIVISA)))
            .andExpect(jsonPath("$.[*].nombreDivisa").value(hasItem(DEFAULT_NOMBRE_DIVISA)));
    }

    @Test
    @Transactional
    void getDivisa() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get the divisa
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL_ID, divisa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(divisa.getId().intValue()))
            .andExpect(jsonPath("$.codigoDivisa").value(DEFAULT_CODIGO_DIVISA))
            .andExpect(jsonPath("$.nombreDivisa").value(DEFAULT_NOMBRE_DIVISA));
    }

    @Test
    @Transactional
    void getDivisasByIdFiltering() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        Long id = divisa.getId();

        defaultDivisaShouldBeFound("id.equals=" + id);
        defaultDivisaShouldNotBeFound("id.notEquals=" + id);

        defaultDivisaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDivisaShouldNotBeFound("id.greaterThan=" + id);

        defaultDivisaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDivisaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaIsEqualToSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa equals to DEFAULT_CODIGO_DIVISA
        defaultDivisaShouldBeFound("codigoDivisa.equals=" + DEFAULT_CODIGO_DIVISA);

        // Get all the divisaList where codigoDivisa equals to UPDATED_CODIGO_DIVISA
        defaultDivisaShouldNotBeFound("codigoDivisa.equals=" + UPDATED_CODIGO_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa not equals to DEFAULT_CODIGO_DIVISA
        defaultDivisaShouldNotBeFound("codigoDivisa.notEquals=" + DEFAULT_CODIGO_DIVISA);

        // Get all the divisaList where codigoDivisa not equals to UPDATED_CODIGO_DIVISA
        defaultDivisaShouldBeFound("codigoDivisa.notEquals=" + UPDATED_CODIGO_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaIsInShouldWork() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa in DEFAULT_CODIGO_DIVISA or UPDATED_CODIGO_DIVISA
        defaultDivisaShouldBeFound("codigoDivisa.in=" + DEFAULT_CODIGO_DIVISA + "," + UPDATED_CODIGO_DIVISA);

        // Get all the divisaList where codigoDivisa equals to UPDATED_CODIGO_DIVISA
        defaultDivisaShouldNotBeFound("codigoDivisa.in=" + UPDATED_CODIGO_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa is not null
        defaultDivisaShouldBeFound("codigoDivisa.specified=true");

        // Get all the divisaList where codigoDivisa is null
        defaultDivisaShouldNotBeFound("codigoDivisa.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaContainsSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa contains DEFAULT_CODIGO_DIVISA
        defaultDivisaShouldBeFound("codigoDivisa.contains=" + DEFAULT_CODIGO_DIVISA);

        // Get all the divisaList where codigoDivisa contains UPDATED_CODIGO_DIVISA
        defaultDivisaShouldNotBeFound("codigoDivisa.contains=" + UPDATED_CODIGO_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByCodigoDivisaNotContainsSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where codigoDivisa does not contain DEFAULT_CODIGO_DIVISA
        defaultDivisaShouldNotBeFound("codigoDivisa.doesNotContain=" + DEFAULT_CODIGO_DIVISA);

        // Get all the divisaList where codigoDivisa does not contain UPDATED_CODIGO_DIVISA
        defaultDivisaShouldBeFound("codigoDivisa.doesNotContain=" + UPDATED_CODIGO_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaIsEqualToSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa equals to DEFAULT_NOMBRE_DIVISA
        defaultDivisaShouldBeFound("nombreDivisa.equals=" + DEFAULT_NOMBRE_DIVISA);

        // Get all the divisaList where nombreDivisa equals to UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldNotBeFound("nombreDivisa.equals=" + UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa not equals to DEFAULT_NOMBRE_DIVISA
        defaultDivisaShouldNotBeFound("nombreDivisa.notEquals=" + DEFAULT_NOMBRE_DIVISA);

        // Get all the divisaList where nombreDivisa not equals to UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldBeFound("nombreDivisa.notEquals=" + UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaIsInShouldWork() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa in DEFAULT_NOMBRE_DIVISA or UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldBeFound("nombreDivisa.in=" + DEFAULT_NOMBRE_DIVISA + "," + UPDATED_NOMBRE_DIVISA);

        // Get all the divisaList where nombreDivisa equals to UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldNotBeFound("nombreDivisa.in=" + UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaIsNullOrNotNull() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa is not null
        defaultDivisaShouldBeFound("nombreDivisa.specified=true");

        // Get all the divisaList where nombreDivisa is null
        defaultDivisaShouldNotBeFound("nombreDivisa.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaContainsSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa contains DEFAULT_NOMBRE_DIVISA
        defaultDivisaShouldBeFound("nombreDivisa.contains=" + DEFAULT_NOMBRE_DIVISA);

        // Get all the divisaList where nombreDivisa contains UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldNotBeFound("nombreDivisa.contains=" + UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void getAllDivisasByNombreDivisaNotContainsSomething() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList where nombreDivisa does not contain DEFAULT_NOMBRE_DIVISA
        defaultDivisaShouldNotBeFound("nombreDivisa.doesNotContain=" + DEFAULT_NOMBRE_DIVISA);

        // Get all the divisaList where nombreDivisa does not contain UPDATED_NOMBRE_DIVISA
        defaultDivisaShouldBeFound("nombreDivisa.doesNotContain=" + UPDATED_NOMBRE_DIVISA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisaShouldBeFound(String filter) throws Exception {
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoDivisa").value(hasItem(DEFAULT_CODIGO_DIVISA)))
            .andExpect(jsonPath("$.[*].nombreDivisa").value(hasItem(DEFAULT_NOMBRE_DIVISA)));

        // Check, that the count call also returns 1
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisaShouldNotBeFound(String filter) throws Exception {
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDivisa() throws Exception {
        // Get the divisa
        restDivisaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDivisa() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa
        Divisa updatedDivisa = divisaRepository.findById(divisa.getId()).get();
        // Disconnect from session so that the updates on updatedDivisa are not directly saved in db
        em.detach(updatedDivisa);
        updatedDivisa.codigoDivisa(UPDATED_CODIGO_DIVISA).nombreDivisa(UPDATED_NOMBRE_DIVISA);
        DivisaDTO divisaDTO = divisaMapper.toDto(updatedDivisa);

        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getCodigoDivisa()).isEqualTo(UPDATED_CODIGO_DIVISA);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void putNonExistingDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDivisaWithPatch() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa using partial update
        Divisa partialUpdatedDivisa = new Divisa();
        partialUpdatedDivisa.setId(divisa.getId());

        partialUpdatedDivisa.nombreDivisa(UPDATED_NOMBRE_DIVISA);

        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisa))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getCodigoDivisa()).isEqualTo(DEFAULT_CODIGO_DIVISA);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void fullUpdateDivisaWithPatch() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa using partial update
        Divisa partialUpdatedDivisa = new Divisa();
        partialUpdatedDivisa.setId(divisa.getId());

        partialUpdatedDivisa.codigoDivisa(UPDATED_CODIGO_DIVISA).nombreDivisa(UPDATED_NOMBRE_DIVISA);

        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisa))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getCodigoDivisa()).isEqualTo(UPDATED_CODIGO_DIVISA);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void patchNonExistingDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, divisaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setId(count.incrementAndGet());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDivisa() throws Exception {
        // Initialize the database
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeDelete = divisaRepository.findAll().size();

        // Delete the divisa
        restDivisaMockMvc
            .perform(delete(ENTITY_API_URL_ID, divisa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
