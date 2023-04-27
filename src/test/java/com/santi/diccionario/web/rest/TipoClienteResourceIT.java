package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.repository.TipoClienteRepository;
import com.santi.diccionario.service.criteria.TipoClienteCriteria;
import com.santi.diccionario.service.dto.TipoClienteDTO;
import com.santi.diccionario.service.mapper.TipoClienteMapper;
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
 * Integration tests for the {@link TipoClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoClienteResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private TipoClienteMapper tipoClienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoClienteMockMvc;

    private TipoCliente tipoCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCliente createEntity(EntityManager em) {
        TipoCliente tipoCliente = new TipoCliente().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE);
        return tipoCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCliente createUpdatedEntity(EntityManager em) {
        TipoCliente tipoCliente = new TipoCliente().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
        return tipoCliente;
    }

    @BeforeEach
    public void initTest() {
        tipoCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoCliente() throws Exception {
        int databaseSizeBeforeCreate = tipoClienteRepository.findAll().size();
        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);
        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeCreate + 1);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createTipoClienteWithExistingId() throws Exception {
        // Create the TipoCliente with an existing ID
        tipoCliente.setId(1L);
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        int databaseSizeBeforeCreate = tipoClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoClienteRepository.findAll().size();
        // set the field null
        tipoCliente.setCodigo(null);

        // Create the TipoCliente, which fails.
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoClienteRepository.findAll().size();
        // set the field null
        tipoCliente.setNombre(null);

        // Create the TipoCliente, which fails.
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoClientes() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getTipoCliente() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get the tipoCliente
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoCliente.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getTipoClientesByIdFiltering() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        Long id = tipoCliente.getId();

        defaultTipoClienteShouldBeFound("id.equals=" + id);
        defaultTipoClienteShouldNotBeFound("id.notEquals=" + id);

        defaultTipoClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo equals to DEFAULT_CODIGO
        defaultTipoClienteShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the tipoClienteList where codigo equals to UPDATED_CODIGO
        defaultTipoClienteShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo not equals to DEFAULT_CODIGO
        defaultTipoClienteShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the tipoClienteList where codigo not equals to UPDATED_CODIGO
        defaultTipoClienteShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultTipoClienteShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the tipoClienteList where codigo equals to UPDATED_CODIGO
        defaultTipoClienteShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo is not null
        defaultTipoClienteShouldBeFound("codigo.specified=true");

        // Get all the tipoClienteList where codigo is null
        defaultTipoClienteShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo contains DEFAULT_CODIGO
        defaultTipoClienteShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the tipoClienteList where codigo contains UPDATED_CODIGO
        defaultTipoClienteShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllTipoClientesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where codigo does not contain DEFAULT_CODIGO
        defaultTipoClienteShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the tipoClienteList where codigo does not contain UPDATED_CODIGO
        defaultTipoClienteShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre equals to DEFAULT_NOMBRE
        defaultTipoClienteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tipoClienteList where nombre equals to UPDATED_NOMBRE
        defaultTipoClienteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre not equals to DEFAULT_NOMBRE
        defaultTipoClienteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the tipoClienteList where nombre not equals to UPDATED_NOMBRE
        defaultTipoClienteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTipoClienteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tipoClienteList where nombre equals to UPDATED_NOMBRE
        defaultTipoClienteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre is not null
        defaultTipoClienteShouldBeFound("nombre.specified=true");

        // Get all the tipoClienteList where nombre is null
        defaultTipoClienteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreContainsSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre contains DEFAULT_NOMBRE
        defaultTipoClienteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the tipoClienteList where nombre contains UPDATED_NOMBRE
        defaultTipoClienteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllTipoClientesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList where nombre does not contain DEFAULT_NOMBRE
        defaultTipoClienteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the tipoClienteList where nombre does not contain UPDATED_NOMBRE
        defaultTipoClienteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoClienteShouldBeFound(String filter) throws Exception {
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoClienteShouldNotBeFound(String filter) throws Exception {
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoCliente() throws Exception {
        // Get the tipoCliente
        restTipoClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoCliente() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente
        TipoCliente updatedTipoCliente = tipoClienteRepository.findById(tipoCliente.getId()).get();
        // Disconnect from session so that the updates on updatedTipoCliente are not directly saved in db
        em.detach(updatedTipoCliente);
        updatedTipoCliente.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(updatedTipoCliente);

        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoClienteWithPatch() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente using partial update
        TipoCliente partialUpdatedTipoCliente = new TipoCliente();
        partialUpdatedTipoCliente.setId(tipoCliente.getId());

        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCliente))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateTipoClienteWithPatch() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente using partial update
        TipoCliente partialUpdatedTipoCliente = new TipoCliente();
        partialUpdatedTipoCliente.setId(tipoCliente.getId());

        partialUpdatedTipoCliente.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);

        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCliente))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setId(count.incrementAndGet());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoCliente() throws Exception {
        // Initialize the database
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeDelete = tipoClienteRepository.findAll().size();

        // Delete the tipoCliente
        restTipoClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
