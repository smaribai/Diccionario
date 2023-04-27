package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.repository.ProvinciaRepository;
import com.santi.diccionario.service.criteria.ProvinciaCriteria;
import com.santi.diccionario.service.dto.ProvinciaDTO;
import com.santi.diccionario.service.mapper.ProvinciaMapper;
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
 * Integration tests for the {@link ProvinciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProvinciaResourceIT {

    private static final String DEFAULT_CODIGO_PROVINCIA = "AAA";
    private static final String UPDATED_CODIGO_PROVINCIA = "BBB";

    private static final String DEFAULT_NOMBRE_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROVINCIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/provincias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ProvinciaMapper provinciaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvinciaMockMvc;

    private Provincia provincia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provincia createEntity(EntityManager em) {
        Provincia provincia = new Provincia().codigoProvincia(DEFAULT_CODIGO_PROVINCIA).nombreProvincia(DEFAULT_NOMBRE_PROVINCIA);
        return provincia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provincia createUpdatedEntity(EntityManager em) {
        Provincia provincia = new Provincia().codigoProvincia(UPDATED_CODIGO_PROVINCIA).nombreProvincia(UPDATED_NOMBRE_PROVINCIA);
        return provincia;
    }

    @BeforeEach
    public void initTest() {
        provincia = createEntity(em);
    }

    @Test
    @Transactional
    void createProvincia() throws Exception {
        int databaseSizeBeforeCreate = provinciaRepository.findAll().size();
        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);
        restProvinciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(provinciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeCreate + 1);
        Provincia testProvincia = provinciaList.get(provinciaList.size() - 1);
        assertThat(testProvincia.getCodigoProvincia()).isEqualTo(DEFAULT_CODIGO_PROVINCIA);
        assertThat(testProvincia.getNombreProvincia()).isEqualTo(DEFAULT_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void createProvinciaWithExistingId() throws Exception {
        // Create the Provincia with an existing ID
        provincia.setId(1L);
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        int databaseSizeBeforeCreate = provinciaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvinciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(provinciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinciaRepository.findAll().size();
        // set the field null
        provincia.setCodigoProvincia(null);

        // Create the Provincia, which fails.
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        restProvinciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(provinciaDTO)))
            .andExpect(status().isBadRequest());

        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreProvinciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = provinciaRepository.findAll().size();
        // set the field null
        provincia.setNombreProvincia(null);

        // Create the Provincia, which fails.
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        restProvinciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(provinciaDTO)))
            .andExpect(status().isBadRequest());

        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProvincias() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provincia.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoProvincia").value(hasItem(DEFAULT_CODIGO_PROVINCIA)))
            .andExpect(jsonPath("$.[*].nombreProvincia").value(hasItem(DEFAULT_NOMBRE_PROVINCIA)));
    }

    @Test
    @Transactional
    void getProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get the provincia
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL_ID, provincia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(provincia.getId().intValue()))
            .andExpect(jsonPath("$.codigoProvincia").value(DEFAULT_CODIGO_PROVINCIA))
            .andExpect(jsonPath("$.nombreProvincia").value(DEFAULT_NOMBRE_PROVINCIA));
    }

    @Test
    @Transactional
    void getProvinciasByIdFiltering() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        Long id = provincia.getId();

        defaultProvinciaShouldBeFound("id.equals=" + id);
        defaultProvinciaShouldNotBeFound("id.notEquals=" + id);

        defaultProvinciaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProvinciaShouldNotBeFound("id.greaterThan=" + id);

        defaultProvinciaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProvinciaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaIsEqualToSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia equals to DEFAULT_CODIGO_PROVINCIA
        defaultProvinciaShouldBeFound("codigoProvincia.equals=" + DEFAULT_CODIGO_PROVINCIA);

        // Get all the provinciaList where codigoProvincia equals to UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldNotBeFound("codigoProvincia.equals=" + UPDATED_CODIGO_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia not equals to DEFAULT_CODIGO_PROVINCIA
        defaultProvinciaShouldNotBeFound("codigoProvincia.notEquals=" + DEFAULT_CODIGO_PROVINCIA);

        // Get all the provinciaList where codigoProvincia not equals to UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldBeFound("codigoProvincia.notEquals=" + UPDATED_CODIGO_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaIsInShouldWork() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia in DEFAULT_CODIGO_PROVINCIA or UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldBeFound("codigoProvincia.in=" + DEFAULT_CODIGO_PROVINCIA + "," + UPDATED_CODIGO_PROVINCIA);

        // Get all the provinciaList where codigoProvincia equals to UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldNotBeFound("codigoProvincia.in=" + UPDATED_CODIGO_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia is not null
        defaultProvinciaShouldBeFound("codigoProvincia.specified=true");

        // Get all the provinciaList where codigoProvincia is null
        defaultProvinciaShouldNotBeFound("codigoProvincia.specified=false");
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaContainsSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia contains DEFAULT_CODIGO_PROVINCIA
        defaultProvinciaShouldBeFound("codigoProvincia.contains=" + DEFAULT_CODIGO_PROVINCIA);

        // Get all the provinciaList where codigoProvincia contains UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldNotBeFound("codigoProvincia.contains=" + UPDATED_CODIGO_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByCodigoProvinciaNotContainsSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where codigoProvincia does not contain DEFAULT_CODIGO_PROVINCIA
        defaultProvinciaShouldNotBeFound("codigoProvincia.doesNotContain=" + DEFAULT_CODIGO_PROVINCIA);

        // Get all the provinciaList where codigoProvincia does not contain UPDATED_CODIGO_PROVINCIA
        defaultProvinciaShouldBeFound("codigoProvincia.doesNotContain=" + UPDATED_CODIGO_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaIsEqualToSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia equals to DEFAULT_NOMBRE_PROVINCIA
        defaultProvinciaShouldBeFound("nombreProvincia.equals=" + DEFAULT_NOMBRE_PROVINCIA);

        // Get all the provinciaList where nombreProvincia equals to UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldNotBeFound("nombreProvincia.equals=" + UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia not equals to DEFAULT_NOMBRE_PROVINCIA
        defaultProvinciaShouldNotBeFound("nombreProvincia.notEquals=" + DEFAULT_NOMBRE_PROVINCIA);

        // Get all the provinciaList where nombreProvincia not equals to UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldBeFound("nombreProvincia.notEquals=" + UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaIsInShouldWork() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia in DEFAULT_NOMBRE_PROVINCIA or UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldBeFound("nombreProvincia.in=" + DEFAULT_NOMBRE_PROVINCIA + "," + UPDATED_NOMBRE_PROVINCIA);

        // Get all the provinciaList where nombreProvincia equals to UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldNotBeFound("nombreProvincia.in=" + UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia is not null
        defaultProvinciaShouldBeFound("nombreProvincia.specified=true");

        // Get all the provinciaList where nombreProvincia is null
        defaultProvinciaShouldNotBeFound("nombreProvincia.specified=false");
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaContainsSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia contains DEFAULT_NOMBRE_PROVINCIA
        defaultProvinciaShouldBeFound("nombreProvincia.contains=" + DEFAULT_NOMBRE_PROVINCIA);

        // Get all the provinciaList where nombreProvincia contains UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldNotBeFound("nombreProvincia.contains=" + UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void getAllProvinciasByNombreProvinciaNotContainsSomething() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        // Get all the provinciaList where nombreProvincia does not contain DEFAULT_NOMBRE_PROVINCIA
        defaultProvinciaShouldNotBeFound("nombreProvincia.doesNotContain=" + DEFAULT_NOMBRE_PROVINCIA);

        // Get all the provinciaList where nombreProvincia does not contain UPDATED_NOMBRE_PROVINCIA
        defaultProvinciaShouldBeFound("nombreProvincia.doesNotContain=" + UPDATED_NOMBRE_PROVINCIA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProvinciaShouldBeFound(String filter) throws Exception {
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provincia.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoProvincia").value(hasItem(DEFAULT_CODIGO_PROVINCIA)))
            .andExpect(jsonPath("$.[*].nombreProvincia").value(hasItem(DEFAULT_NOMBRE_PROVINCIA)));

        // Check, that the count call also returns 1
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProvinciaShouldNotBeFound(String filter) throws Exception {
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProvinciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProvincia() throws Exception {
        // Get the provincia
        restProvinciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();

        // Update the provincia
        Provincia updatedProvincia = provinciaRepository.findById(provincia.getId()).get();
        // Disconnect from session so that the updates on updatedProvincia are not directly saved in db
        em.detach(updatedProvincia);
        updatedProvincia.codigoProvincia(UPDATED_CODIGO_PROVINCIA).nombreProvincia(UPDATED_NOMBRE_PROVINCIA);
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(updatedProvincia);

        restProvinciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provinciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
        Provincia testProvincia = provinciaList.get(provinciaList.size() - 1);
        assertThat(testProvincia.getCodigoProvincia()).isEqualTo(UPDATED_CODIGO_PROVINCIA);
        assertThat(testProvincia.getNombreProvincia()).isEqualTo(UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void putNonExistingProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provinciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(provinciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvinciaWithPatch() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();

        // Update the provincia using partial update
        Provincia partialUpdatedProvincia = new Provincia();
        partialUpdatedProvincia.setId(provincia.getId());

        partialUpdatedProvincia.nombreProvincia(UPDATED_NOMBRE_PROVINCIA);

        restProvinciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvincia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvincia))
            )
            .andExpect(status().isOk());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
        Provincia testProvincia = provinciaList.get(provinciaList.size() - 1);
        assertThat(testProvincia.getCodigoProvincia()).isEqualTo(DEFAULT_CODIGO_PROVINCIA);
        assertThat(testProvincia.getNombreProvincia()).isEqualTo(UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void fullUpdateProvinciaWithPatch() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();

        // Update the provincia using partial update
        Provincia partialUpdatedProvincia = new Provincia();
        partialUpdatedProvincia.setId(provincia.getId());

        partialUpdatedProvincia.codigoProvincia(UPDATED_CODIGO_PROVINCIA).nombreProvincia(UPDATED_NOMBRE_PROVINCIA);

        restProvinciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvincia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProvincia))
            )
            .andExpect(status().isOk());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
        Provincia testProvincia = provinciaList.get(provinciaList.size() - 1);
        assertThat(testProvincia.getCodigoProvincia()).isEqualTo(UPDATED_CODIGO_PROVINCIA);
        assertThat(testProvincia.getNombreProvincia()).isEqualTo(UPDATED_NOMBRE_PROVINCIA);
    }

    @Test
    @Transactional
    void patchNonExistingProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, provinciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvincia() throws Exception {
        int databaseSizeBeforeUpdate = provinciaRepository.findAll().size();
        provincia.setId(count.incrementAndGet());

        // Create the Provincia
        ProvinciaDTO provinciaDTO = provinciaMapper.toDto(provincia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinciaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(provinciaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provincia in the database
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvincia() throws Exception {
        // Initialize the database
        provinciaRepository.saveAndFlush(provincia);

        int databaseSizeBeforeDelete = provinciaRepository.findAll().size();

        // Delete the provincia
        restProvinciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, provincia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Provincia> provinciaList = provinciaRepository.findAll();
        assertThat(provinciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
