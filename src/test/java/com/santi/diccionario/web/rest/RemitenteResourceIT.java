package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.repository.RemitenteRepository;
import com.santi.diccionario.service.criteria.RemitenteCriteria;
import com.santi.diccionario.service.dto.RemitenteDTO;
import com.santi.diccionario.service.mapper.RemitenteMapper;
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
 * Integration tests for the {@link RemitenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemitenteResourceIT {

    private static final String DEFAULT_ID_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_REMITENTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remitentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemitenteRepository remitenteRepository;

    @Autowired
    private RemitenteMapper remitenteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemitenteMockMvc;

    private Remitente remitente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remitente createEntity(EntityManager em) {
        Remitente remitente = new Remitente()
            .idRemitente(DEFAULT_ID_REMITENTE)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .direccion(DEFAULT_DIRECCION);
        return remitente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remitente createUpdatedEntity(EntityManager em) {
        Remitente remitente = new Remitente()
            .idRemitente(UPDATED_ID_REMITENTE)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);
        return remitente;
    }

    @BeforeEach
    public void initTest() {
        remitente = createEntity(em);
    }

    @Test
    @Transactional
    void createRemitente() throws Exception {
        int databaseSizeBeforeCreate = remitenteRepository.findAll().size();
        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);
        restRemitenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remitenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeCreate + 1);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getIdRemitente()).isEqualTo(DEFAULT_ID_REMITENTE);
        assertThat(testRemitente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRemitente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRemitente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void createRemitenteWithExistingId() throws Exception {
        // Create the Remitente with an existing ID
        remitente.setId(1L);
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        int databaseSizeBeforeCreate = remitenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemitenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remitenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdRemitenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = remitenteRepository.findAll().size();
        // set the field null
        remitente.setIdRemitente(null);

        // Create the Remitente, which fails.
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        restRemitenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remitenteDTO)))
            .andExpect(status().isBadRequest());

        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = remitenteRepository.findAll().size();
        // set the field null
        remitente.setNombre(null);

        // Create the Remitente, which fails.
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        restRemitenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remitenteDTO)))
            .andExpect(status().isBadRequest());

        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRemitentes() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remitente.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRemitente").value(hasItem(DEFAULT_ID_REMITENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get the remitente
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL_ID, remitente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remitente.getId().intValue()))
            .andExpect(jsonPath("$.idRemitente").value(DEFAULT_ID_REMITENTE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getRemitentesByIdFiltering() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        Long id = remitente.getId();

        defaultRemitenteShouldBeFound("id.equals=" + id);
        defaultRemitenteShouldNotBeFound("id.notEquals=" + id);

        defaultRemitenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRemitenteShouldNotBeFound("id.greaterThan=" + id);

        defaultRemitenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRemitenteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteIsEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente equals to DEFAULT_ID_REMITENTE
        defaultRemitenteShouldBeFound("idRemitente.equals=" + DEFAULT_ID_REMITENTE);

        // Get all the remitenteList where idRemitente equals to UPDATED_ID_REMITENTE
        defaultRemitenteShouldNotBeFound("idRemitente.equals=" + UPDATED_ID_REMITENTE);
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente not equals to DEFAULT_ID_REMITENTE
        defaultRemitenteShouldNotBeFound("idRemitente.notEquals=" + DEFAULT_ID_REMITENTE);

        // Get all the remitenteList where idRemitente not equals to UPDATED_ID_REMITENTE
        defaultRemitenteShouldBeFound("idRemitente.notEquals=" + UPDATED_ID_REMITENTE);
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteIsInShouldWork() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente in DEFAULT_ID_REMITENTE or UPDATED_ID_REMITENTE
        defaultRemitenteShouldBeFound("idRemitente.in=" + DEFAULT_ID_REMITENTE + "," + UPDATED_ID_REMITENTE);

        // Get all the remitenteList where idRemitente equals to UPDATED_ID_REMITENTE
        defaultRemitenteShouldNotBeFound("idRemitente.in=" + UPDATED_ID_REMITENTE);
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente is not null
        defaultRemitenteShouldBeFound("idRemitente.specified=true");

        // Get all the remitenteList where idRemitente is null
        defaultRemitenteShouldNotBeFound("idRemitente.specified=false");
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente contains DEFAULT_ID_REMITENTE
        defaultRemitenteShouldBeFound("idRemitente.contains=" + DEFAULT_ID_REMITENTE);

        // Get all the remitenteList where idRemitente contains UPDATED_ID_REMITENTE
        defaultRemitenteShouldNotBeFound("idRemitente.contains=" + UPDATED_ID_REMITENTE);
    }

    @Test
    @Transactional
    void getAllRemitentesByIdRemitenteNotContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where idRemitente does not contain DEFAULT_ID_REMITENTE
        defaultRemitenteShouldNotBeFound("idRemitente.doesNotContain=" + DEFAULT_ID_REMITENTE);

        // Get all the remitenteList where idRemitente does not contain UPDATED_ID_REMITENTE
        defaultRemitenteShouldBeFound("idRemitente.doesNotContain=" + UPDATED_ID_REMITENTE);
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre equals to DEFAULT_NOMBRE
        defaultRemitenteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the remitenteList where nombre equals to UPDATED_NOMBRE
        defaultRemitenteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre not equals to DEFAULT_NOMBRE
        defaultRemitenteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the remitenteList where nombre not equals to UPDATED_NOMBRE
        defaultRemitenteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultRemitenteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the remitenteList where nombre equals to UPDATED_NOMBRE
        defaultRemitenteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre is not null
        defaultRemitenteShouldBeFound("nombre.specified=true");

        // Get all the remitenteList where nombre is null
        defaultRemitenteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre contains DEFAULT_NOMBRE
        defaultRemitenteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the remitenteList where nombre contains UPDATED_NOMBRE
        defaultRemitenteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllRemitentesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where nombre does not contain DEFAULT_NOMBRE
        defaultRemitenteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the remitenteList where nombre does not contain UPDATED_NOMBRE
        defaultRemitenteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion equals to DEFAULT_DESCRIPCION
        defaultRemitenteShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the remitenteList where descripcion equals to UPDATED_DESCRIPCION
        defaultRemitenteShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultRemitenteShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the remitenteList where descripcion not equals to UPDATED_DESCRIPCION
        defaultRemitenteShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultRemitenteShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the remitenteList where descripcion equals to UPDATED_DESCRIPCION
        defaultRemitenteShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion is not null
        defaultRemitenteShouldBeFound("descripcion.specified=true");

        // Get all the remitenteList where descripcion is null
        defaultRemitenteShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion contains DEFAULT_DESCRIPCION
        defaultRemitenteShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the remitenteList where descripcion contains UPDATED_DESCRIPCION
        defaultRemitenteShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultRemitenteShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the remitenteList where descripcion does not contain UPDATED_DESCRIPCION
        defaultRemitenteShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion equals to DEFAULT_DIRECCION
        defaultRemitenteShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the remitenteList where direccion equals to UPDATED_DIRECCION
        defaultRemitenteShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion not equals to DEFAULT_DIRECCION
        defaultRemitenteShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the remitenteList where direccion not equals to UPDATED_DIRECCION
        defaultRemitenteShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultRemitenteShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the remitenteList where direccion equals to UPDATED_DIRECCION
        defaultRemitenteShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion is not null
        defaultRemitenteShouldBeFound("direccion.specified=true");

        // Get all the remitenteList where direccion is null
        defaultRemitenteShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion contains DEFAULT_DIRECCION
        defaultRemitenteShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the remitenteList where direccion contains UPDATED_DIRECCION
        defaultRemitenteShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllRemitentesByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList where direccion does not contain DEFAULT_DIRECCION
        defaultRemitenteShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the remitenteList where direccion does not contain UPDATED_DIRECCION
        defaultRemitenteShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRemitenteShouldBeFound(String filter) throws Exception {
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remitente.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRemitente").value(hasItem(DEFAULT_ID_REMITENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));

        // Check, that the count call also returns 1
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRemitenteShouldNotBeFound(String filter) throws Exception {
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRemitenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRemitente() throws Exception {
        // Get the remitente
        restRemitenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();

        // Update the remitente
        Remitente updatedRemitente = remitenteRepository.findById(remitente.getId()).get();
        // Disconnect from session so that the updates on updatedRemitente are not directly saved in db
        em.detach(updatedRemitente);
        updatedRemitente
            .idRemitente(UPDATED_ID_REMITENTE)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(updatedRemitente);

        restRemitenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remitenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getIdRemitente()).isEqualTo(UPDATED_ID_REMITENTE);
        assertThat(testRemitente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRemitente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRemitente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void putNonExistingRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remitenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remitenteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemitenteWithPatch() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();

        // Update the remitente using partial update
        Remitente partialUpdatedRemitente = new Remitente();
        partialUpdatedRemitente.setId(remitente.getId());

        partialUpdatedRemitente.idRemitente(UPDATED_ID_REMITENTE).descripcion(UPDATED_DESCRIPCION);

        restRemitenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemitente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemitente))
            )
            .andExpect(status().isOk());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getIdRemitente()).isEqualTo(UPDATED_ID_REMITENTE);
        assertThat(testRemitente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRemitente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRemitente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void fullUpdateRemitenteWithPatch() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();

        // Update the remitente using partial update
        Remitente partialUpdatedRemitente = new Remitente();
        partialUpdatedRemitente.setId(remitente.getId());

        partialUpdatedRemitente
            .idRemitente(UPDATED_ID_REMITENTE)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);

        restRemitenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemitente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemitente))
            )
            .andExpect(status().isOk());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getIdRemitente()).isEqualTo(UPDATED_ID_REMITENTE);
        assertThat(testRemitente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRemitente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRemitente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void patchNonExistingRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remitenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();
        remitente.setId(count.incrementAndGet());

        // Create the Remitente
        RemitenteDTO remitenteDTO = remitenteMapper.toDto(remitente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemitenteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(remitenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeDelete = remitenteRepository.findAll().size();

        // Delete the remitente
        restRemitenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, remitente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
