package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.repository.PaisRepository;
import com.santi.diccionario.service.criteria.PaisCriteria;
import com.santi.diccionario.service.dto.PaisDTO;
import com.santi.diccionario.service.mapper.PaisMapper;
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
 * Integration tests for the {@link PaisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaisResourceIT {

    private static final String DEFAULT_CODIGO_PAIS = "AA";
    private static final String UPDATED_CODIGO_PAIS = "BB";

    private static final String DEFAULT_NOMBRE_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PAIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisMapper paisMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaisMockMvc;

    private Pais pais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createEntity(EntityManager em) {
        Pais pais = new Pais().codigoPais(DEFAULT_CODIGO_PAIS).nombrePais(DEFAULT_NOMBRE_PAIS);
        return pais;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createUpdatedEntity(EntityManager em) {
        Pais pais = new Pais().codigoPais(UPDATED_CODIGO_PAIS).nombrePais(UPDATED_NOMBRE_PAIS);
        return pais;
    }

    @BeforeEach
    public void initTest() {
        pais = createEntity(em);
    }

    @Test
    @Transactional
    void createPais() throws Exception {
        int databaseSizeBeforeCreate = paisRepository.findAll().size();
        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);
        restPaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isCreated());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate + 1);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getCodigoPais()).isEqualTo(DEFAULT_CODIGO_PAIS);
        assertThat(testPais.getNombrePais()).isEqualTo(DEFAULT_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void createPaisWithExistingId() throws Exception {
        // Create the Pais with an existing ID
        pais.setId(1L);
        PaisDTO paisDTO = paisMapper.toDto(pais);

        int databaseSizeBeforeCreate = paisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = paisRepository.findAll().size();
        // set the field null
        pais.setCodigoPais(null);

        // Create the Pais, which fails.
        PaisDTO paisDTO = paisMapper.toDto(pais);

        restPaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isBadRequest());

        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombrePaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = paisRepository.findAll().size();
        // set the field null
        pais.setNombrePais(null);

        // Create the Pais, which fails.
        PaisDTO paisDTO = paisMapper.toDto(pais);

        restPaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isBadRequest());

        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPais").value(hasItem(DEFAULT_CODIGO_PAIS)))
            .andExpect(jsonPath("$.[*].nombrePais").value(hasItem(DEFAULT_NOMBRE_PAIS)));
    }

    @Test
    @Transactional
    void getPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get the pais
        restPaisMockMvc
            .perform(get(ENTITY_API_URL_ID, pais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pais.getId().intValue()))
            .andExpect(jsonPath("$.codigoPais").value(DEFAULT_CODIGO_PAIS))
            .andExpect(jsonPath("$.nombrePais").value(DEFAULT_NOMBRE_PAIS));
    }

    @Test
    @Transactional
    void getPaisByIdFiltering() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        Long id = pais.getId();

        defaultPaisShouldBeFound("id.equals=" + id);
        defaultPaisShouldNotBeFound("id.notEquals=" + id);

        defaultPaisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaisShouldNotBeFound("id.greaterThan=" + id);

        defaultPaisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaisShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais equals to DEFAULT_CODIGO_PAIS
        defaultPaisShouldBeFound("codigoPais.equals=" + DEFAULT_CODIGO_PAIS);

        // Get all the paisList where codigoPais equals to UPDATED_CODIGO_PAIS
        defaultPaisShouldNotBeFound("codigoPais.equals=" + UPDATED_CODIGO_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais not equals to DEFAULT_CODIGO_PAIS
        defaultPaisShouldNotBeFound("codigoPais.notEquals=" + DEFAULT_CODIGO_PAIS);

        // Get all the paisList where codigoPais not equals to UPDATED_CODIGO_PAIS
        defaultPaisShouldBeFound("codigoPais.notEquals=" + UPDATED_CODIGO_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisIsInShouldWork() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais in DEFAULT_CODIGO_PAIS or UPDATED_CODIGO_PAIS
        defaultPaisShouldBeFound("codigoPais.in=" + DEFAULT_CODIGO_PAIS + "," + UPDATED_CODIGO_PAIS);

        // Get all the paisList where codigoPais equals to UPDATED_CODIGO_PAIS
        defaultPaisShouldNotBeFound("codigoPais.in=" + UPDATED_CODIGO_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais is not null
        defaultPaisShouldBeFound("codigoPais.specified=true");

        // Get all the paisList where codigoPais is null
        defaultPaisShouldNotBeFound("codigoPais.specified=false");
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisContainsSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais contains DEFAULT_CODIGO_PAIS
        defaultPaisShouldBeFound("codigoPais.contains=" + DEFAULT_CODIGO_PAIS);

        // Get all the paisList where codigoPais contains UPDATED_CODIGO_PAIS
        defaultPaisShouldNotBeFound("codigoPais.contains=" + UPDATED_CODIGO_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByCodigoPaisNotContainsSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where codigoPais does not contain DEFAULT_CODIGO_PAIS
        defaultPaisShouldNotBeFound("codigoPais.doesNotContain=" + DEFAULT_CODIGO_PAIS);

        // Get all the paisList where codigoPais does not contain UPDATED_CODIGO_PAIS
        defaultPaisShouldBeFound("codigoPais.doesNotContain=" + UPDATED_CODIGO_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisIsEqualToSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais equals to DEFAULT_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.equals=" + DEFAULT_NOMBRE_PAIS);

        // Get all the paisList where nombrePais equals to UPDATED_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.equals=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais not equals to DEFAULT_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.notEquals=" + DEFAULT_NOMBRE_PAIS);

        // Get all the paisList where nombrePais not equals to UPDATED_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.notEquals=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisIsInShouldWork() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais in DEFAULT_NOMBRE_PAIS or UPDATED_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.in=" + DEFAULT_NOMBRE_PAIS + "," + UPDATED_NOMBRE_PAIS);

        // Get all the paisList where nombrePais equals to UPDATED_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.in=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais is not null
        defaultPaisShouldBeFound("nombrePais.specified=true");

        // Get all the paisList where nombrePais is null
        defaultPaisShouldNotBeFound("nombrePais.specified=false");
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisContainsSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais contains DEFAULT_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.contains=" + DEFAULT_NOMBRE_PAIS);

        // Get all the paisList where nombrePais contains UPDATED_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.contains=" + UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void getAllPaisByNombrePaisNotContainsSomething() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList where nombrePais does not contain DEFAULT_NOMBRE_PAIS
        defaultPaisShouldNotBeFound("nombrePais.doesNotContain=" + DEFAULT_NOMBRE_PAIS);

        // Get all the paisList where nombrePais does not contain UPDATED_NOMBRE_PAIS
        defaultPaisShouldBeFound("nombrePais.doesNotContain=" + UPDATED_NOMBRE_PAIS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaisShouldBeFound(String filter) throws Exception {
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPais").value(hasItem(DEFAULT_CODIGO_PAIS)))
            .andExpect(jsonPath("$.[*].nombrePais").value(hasItem(DEFAULT_NOMBRE_PAIS)));

        // Check, that the count call also returns 1
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaisShouldNotBeFound(String filter) throws Exception {
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPais() throws Exception {
        // Get the pais
        restPaisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Update the pais
        Pais updatedPais = paisRepository.findById(pais.getId()).get();
        // Disconnect from session so that the updates on updatedPais are not directly saved in db
        em.detach(updatedPais);
        updatedPais.codigoPais(UPDATED_CODIGO_PAIS).nombrePais(UPDATED_NOMBRE_PAIS);
        PaisDTO paisDTO = paisMapper.toDto(updatedPais);

        restPaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paisDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getCodigoPais()).isEqualTo(UPDATED_CODIGO_PAIS);
        assertThat(testPais.getNombrePais()).isEqualTo(UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void putNonExistingPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaisWithPatch() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Update the pais using partial update
        Pais partialUpdatedPais = new Pais();
        partialUpdatedPais.setId(pais.getId());

        partialUpdatedPais.codigoPais(UPDATED_CODIGO_PAIS).nombrePais(UPDATED_NOMBRE_PAIS);

        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPais))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getCodigoPais()).isEqualTo(UPDATED_CODIGO_PAIS);
        assertThat(testPais.getNombrePais()).isEqualTo(UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void fullUpdatePaisWithPatch() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Update the pais using partial update
        Pais partialUpdatedPais = new Pais();
        partialUpdatedPais.setId(pais.getId());

        partialUpdatedPais.codigoPais(UPDATED_CODIGO_PAIS).nombrePais(UPDATED_NOMBRE_PAIS);

        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPais.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPais))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getCodigoPais()).isEqualTo(UPDATED_CODIGO_PAIS);
        assertThat(testPais.getNombrePais()).isEqualTo(UPDATED_NOMBRE_PAIS);
    }

    @Test
    @Transactional
    void patchNonExistingPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paisDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();
        pais.setId(count.incrementAndGet());

        // Create the Pais
        PaisDTO paisDTO = paisMapper.toDto(pais);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paisDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        int databaseSizeBeforeDelete = paisRepository.findAll().size();

        // Delete the pais
        restPaisMockMvc
            .perform(delete(ENTITY_API_URL_ID, pais.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
