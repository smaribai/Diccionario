package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.repository.DestinatarioRepository;
import com.santi.diccionario.service.criteria.DestinatarioCriteria;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import com.santi.diccionario.service.mapper.DestinatarioMapper;
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
 * Integration tests for the {@link DestinatarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DestinatarioResourceIT {

    private static final String DEFAULT_ID_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_DESTINATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/destinatarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DestinatarioRepository destinatarioRepository;

    @Autowired
    private DestinatarioMapper destinatarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDestinatarioMockMvc;

    private Destinatario destinatario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destinatario createEntity(EntityManager em) {
        Destinatario destinatario = new Destinatario()
            .idDestinatario(DEFAULT_ID_DESTINATARIO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .direccion(DEFAULT_DIRECCION);
        return destinatario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destinatario createUpdatedEntity(EntityManager em) {
        Destinatario destinatario = new Destinatario()
            .idDestinatario(UPDATED_ID_DESTINATARIO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);
        return destinatario;
    }

    @BeforeEach
    public void initTest() {
        destinatario = createEntity(em);
    }

    @Test
    @Transactional
    void createDestinatario() throws Exception {
        int databaseSizeBeforeCreate = destinatarioRepository.findAll().size();
        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);
        restDestinatarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeCreate + 1);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getIdDestinatario()).isEqualTo(DEFAULT_ID_DESTINATARIO);
        assertThat(testDestinatario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDestinatario.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testDestinatario.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void createDestinatarioWithExistingId() throws Exception {
        // Create the Destinatario with an existing ID
        destinatario.setId(1L);
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        int databaseSizeBeforeCreate = destinatarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinatarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDestinatarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = destinatarioRepository.findAll().size();
        // set the field null
        destinatario.setIdDestinatario(null);

        // Create the Destinatario, which fails.
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        restDestinatarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = destinatarioRepository.findAll().size();
        // set the field null
        destinatario.setNombre(null);

        // Create the Destinatario, which fails.
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        restDestinatarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDestinatarios() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destinatario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDestinatario").value(hasItem(DEFAULT_ID_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get the destinatario
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL_ID, destinatario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(destinatario.getId().intValue()))
            .andExpect(jsonPath("$.idDestinatario").value(DEFAULT_ID_DESTINATARIO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getDestinatariosByIdFiltering() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        Long id = destinatario.getId();

        defaultDestinatarioShouldBeFound("id.equals=" + id);
        defaultDestinatarioShouldNotBeFound("id.notEquals=" + id);

        defaultDestinatarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDestinatarioShouldNotBeFound("id.greaterThan=" + id);

        defaultDestinatarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDestinatarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario equals to DEFAULT_ID_DESTINATARIO
        defaultDestinatarioShouldBeFound("idDestinatario.equals=" + DEFAULT_ID_DESTINATARIO);

        // Get all the destinatarioList where idDestinatario equals to UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldNotBeFound("idDestinatario.equals=" + UPDATED_ID_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario not equals to DEFAULT_ID_DESTINATARIO
        defaultDestinatarioShouldNotBeFound("idDestinatario.notEquals=" + DEFAULT_ID_DESTINATARIO);

        // Get all the destinatarioList where idDestinatario not equals to UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldBeFound("idDestinatario.notEquals=" + UPDATED_ID_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioIsInShouldWork() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario in DEFAULT_ID_DESTINATARIO or UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldBeFound("idDestinatario.in=" + DEFAULT_ID_DESTINATARIO + "," + UPDATED_ID_DESTINATARIO);

        // Get all the destinatarioList where idDestinatario equals to UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldNotBeFound("idDestinatario.in=" + UPDATED_ID_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario is not null
        defaultDestinatarioShouldBeFound("idDestinatario.specified=true");

        // Get all the destinatarioList where idDestinatario is null
        defaultDestinatarioShouldNotBeFound("idDestinatario.specified=false");
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario contains DEFAULT_ID_DESTINATARIO
        defaultDestinatarioShouldBeFound("idDestinatario.contains=" + DEFAULT_ID_DESTINATARIO);

        // Get all the destinatarioList where idDestinatario contains UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldNotBeFound("idDestinatario.contains=" + UPDATED_ID_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllDestinatariosByIdDestinatarioNotContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where idDestinatario does not contain DEFAULT_ID_DESTINATARIO
        defaultDestinatarioShouldNotBeFound("idDestinatario.doesNotContain=" + DEFAULT_ID_DESTINATARIO);

        // Get all the destinatarioList where idDestinatario does not contain UPDATED_ID_DESTINATARIO
        defaultDestinatarioShouldBeFound("idDestinatario.doesNotContain=" + UPDATED_ID_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre equals to DEFAULT_NOMBRE
        defaultDestinatarioShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the destinatarioList where nombre equals to UPDATED_NOMBRE
        defaultDestinatarioShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre not equals to DEFAULT_NOMBRE
        defaultDestinatarioShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the destinatarioList where nombre not equals to UPDATED_NOMBRE
        defaultDestinatarioShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultDestinatarioShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the destinatarioList where nombre equals to UPDATED_NOMBRE
        defaultDestinatarioShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre is not null
        defaultDestinatarioShouldBeFound("nombre.specified=true");

        // Get all the destinatarioList where nombre is null
        defaultDestinatarioShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre contains DEFAULT_NOMBRE
        defaultDestinatarioShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the destinatarioList where nombre contains UPDATED_NOMBRE
        defaultDestinatarioShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDestinatariosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where nombre does not contain DEFAULT_NOMBRE
        defaultDestinatarioShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the destinatarioList where nombre does not contain UPDATED_NOMBRE
        defaultDestinatarioShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion equals to DEFAULT_DESCRIPCION
        defaultDestinatarioShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the destinatarioList where descripcion equals to UPDATED_DESCRIPCION
        defaultDestinatarioShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultDestinatarioShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the destinatarioList where descripcion not equals to UPDATED_DESCRIPCION
        defaultDestinatarioShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultDestinatarioShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the destinatarioList where descripcion equals to UPDATED_DESCRIPCION
        defaultDestinatarioShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion is not null
        defaultDestinatarioShouldBeFound("descripcion.specified=true");

        // Get all the destinatarioList where descripcion is null
        defaultDestinatarioShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion contains DEFAULT_DESCRIPCION
        defaultDestinatarioShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the destinatarioList where descripcion contains UPDATED_DESCRIPCION
        defaultDestinatarioShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultDestinatarioShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the destinatarioList where descripcion does not contain UPDATED_DESCRIPCION
        defaultDestinatarioShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion equals to DEFAULT_DIRECCION
        defaultDestinatarioShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the destinatarioList where direccion equals to UPDATED_DIRECCION
        defaultDestinatarioShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion not equals to DEFAULT_DIRECCION
        defaultDestinatarioShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the destinatarioList where direccion not equals to UPDATED_DIRECCION
        defaultDestinatarioShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultDestinatarioShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the destinatarioList where direccion equals to UPDATED_DIRECCION
        defaultDestinatarioShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion is not null
        defaultDestinatarioShouldBeFound("direccion.specified=true");

        // Get all the destinatarioList where direccion is null
        defaultDestinatarioShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion contains DEFAULT_DIRECCION
        defaultDestinatarioShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the destinatarioList where direccion contains UPDATED_DIRECCION
        defaultDestinatarioShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllDestinatariosByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList where direccion does not contain DEFAULT_DIRECCION
        defaultDestinatarioShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the destinatarioList where direccion does not contain UPDATED_DIRECCION
        defaultDestinatarioShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDestinatarioShouldBeFound(String filter) throws Exception {
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destinatario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDestinatario").value(hasItem(DEFAULT_ID_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));

        // Check, that the count call also returns 1
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDestinatarioShouldNotBeFound(String filter) throws Exception {
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDestinatarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDestinatario() throws Exception {
        // Get the destinatario
        restDestinatarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();

        // Update the destinatario
        Destinatario updatedDestinatario = destinatarioRepository.findById(destinatario.getId()).get();
        // Disconnect from session so that the updates on updatedDestinatario are not directly saved in db
        em.detach(updatedDestinatario);
        updatedDestinatario
            .idDestinatario(UPDATED_ID_DESTINATARIO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(updatedDestinatario);

        restDestinatarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, destinatarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getIdDestinatario()).isEqualTo(UPDATED_ID_DESTINATARIO);
        assertThat(testDestinatario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDestinatario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testDestinatario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void putNonExistingDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, destinatarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDestinatarioWithPatch() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();

        // Update the destinatario using partial update
        Destinatario partialUpdatedDestinatario = new Destinatario();
        partialUpdatedDestinatario.setId(destinatario.getId());

        partialUpdatedDestinatario.idDestinatario(UPDATED_ID_DESTINATARIO).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restDestinatarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDestinatario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDestinatario))
            )
            .andExpect(status().isOk());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getIdDestinatario()).isEqualTo(UPDATED_ID_DESTINATARIO);
        assertThat(testDestinatario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDestinatario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testDestinatario.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void fullUpdateDestinatarioWithPatch() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();

        // Update the destinatario using partial update
        Destinatario partialUpdatedDestinatario = new Destinatario();
        partialUpdatedDestinatario.setId(destinatario.getId());

        partialUpdatedDestinatario
            .idDestinatario(UPDATED_ID_DESTINATARIO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);

        restDestinatarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDestinatario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDestinatario))
            )
            .andExpect(status().isOk());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getIdDestinatario()).isEqualTo(UPDATED_ID_DESTINATARIO);
        assertThat(testDestinatario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDestinatario.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testDestinatario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void patchNonExistingDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, destinatarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();
        destinatario.setId(count.incrementAndGet());

        // Create the Destinatario
        DestinatarioDTO destinatarioDTO = destinatarioMapper.toDto(destinatario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(destinatarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeDelete = destinatarioRepository.findAll().size();

        // Delete the destinatario
        restDestinatarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, destinatario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
