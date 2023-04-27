package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Traduccion;
import com.santi.diccionario.repository.TraduccionRepository;
import com.santi.diccionario.service.TraduccionService;
import com.santi.diccionario.service.criteria.TraduccionCriteria;
import com.santi.diccionario.service.dto.TraduccionDTO;
import com.santi.diccionario.service.mapper.TraduccionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TraduccionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TraduccionResourceIT {

    private static final String DEFAULT_TEXTO_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_DESTINO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/traduccions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TraduccionRepository traduccionRepository;

    @Mock
    private TraduccionRepository traduccionRepositoryMock;

    @Autowired
    private TraduccionMapper traduccionMapper;

    @Mock
    private TraduccionService traduccionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTraduccionMockMvc;

    private Traduccion traduccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traduccion createEntity(EntityManager em) {
        Traduccion traduccion = new Traduccion().textoOrigen(DEFAULT_TEXTO_ORIGEN).textoDestino(DEFAULT_TEXTO_DESTINO);
        // Add required entity
        Idioma idioma;
        if (TestUtil.findAll(em, Idioma.class).isEmpty()) {
            idioma = IdiomaResourceIT.createEntity(em);
            em.persist(idioma);
            em.flush();
        } else {
            idioma = TestUtil.findAll(em, Idioma.class).get(0);
        }
        traduccion.setIdiomaOrigen(idioma);
        // Add required entity
        traduccion.setIdiomaDestino(idioma);
        return traduccion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traduccion createUpdatedEntity(EntityManager em) {
        Traduccion traduccion = new Traduccion().textoOrigen(UPDATED_TEXTO_ORIGEN).textoDestino(UPDATED_TEXTO_DESTINO);
        // Add required entity
        Idioma idioma;
        if (TestUtil.findAll(em, Idioma.class).isEmpty()) {
            idioma = IdiomaResourceIT.createUpdatedEntity(em);
            em.persist(idioma);
            em.flush();
        } else {
            idioma = TestUtil.findAll(em, Idioma.class).get(0);
        }
        traduccion.setIdiomaOrigen(idioma);
        // Add required entity
        traduccion.setIdiomaDestino(idioma);
        return traduccion;
    }

    @BeforeEach
    public void initTest() {
        traduccion = createEntity(em);
    }

    @Test
    @Transactional
    void createTraduccion() throws Exception {
        int databaseSizeBeforeCreate = traduccionRepository.findAll().size();
        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);
        restTraduccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traduccionDTO)))
            .andExpect(status().isCreated());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeCreate + 1);
        Traduccion testTraduccion = traduccionList.get(traduccionList.size() - 1);
        assertThat(testTraduccion.getTextoOrigen()).isEqualTo(DEFAULT_TEXTO_ORIGEN);
        assertThat(testTraduccion.getTextoDestino()).isEqualTo(DEFAULT_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void createTraduccionWithExistingId() throws Exception {
        // Create the Traduccion with an existing ID
        traduccion.setId(1L);
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        int databaseSizeBeforeCreate = traduccionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraduccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traduccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTextoOrigenIsRequired() throws Exception {
        int databaseSizeBeforeTest = traduccionRepository.findAll().size();
        // set the field null
        traduccion.setTextoOrigen(null);

        // Create the Traduccion, which fails.
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        restTraduccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traduccionDTO)))
            .andExpect(status().isBadRequest());

        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTextoDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = traduccionRepository.findAll().size();
        // set the field null
        traduccion.setTextoDestino(null);

        // Create the Traduccion, which fails.
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        restTraduccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traduccionDTO)))
            .andExpect(status().isBadRequest());

        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTraduccions() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traduccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].textoOrigen").value(hasItem(DEFAULT_TEXTO_ORIGEN)))
            .andExpect(jsonPath("$.[*].textoDestino").value(hasItem(DEFAULT_TEXTO_DESTINO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTraduccionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(traduccionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTraduccionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(traduccionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTraduccionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(traduccionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTraduccionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(traduccionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTraduccion() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get the traduccion
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL_ID, traduccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traduccion.getId().intValue()))
            .andExpect(jsonPath("$.textoOrigen").value(DEFAULT_TEXTO_ORIGEN))
            .andExpect(jsonPath("$.textoDestino").value(DEFAULT_TEXTO_DESTINO));
    }

    @Test
    @Transactional
    void getTraduccionsByIdFiltering() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        Long id = traduccion.getId();

        defaultTraduccionShouldBeFound("id.equals=" + id);
        defaultTraduccionShouldNotBeFound("id.notEquals=" + id);

        defaultTraduccionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTraduccionShouldNotBeFound("id.greaterThan=" + id);

        defaultTraduccionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTraduccionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen equals to DEFAULT_TEXTO_ORIGEN
        defaultTraduccionShouldBeFound("textoOrigen.equals=" + DEFAULT_TEXTO_ORIGEN);

        // Get all the traduccionList where textoOrigen equals to UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldNotBeFound("textoOrigen.equals=" + UPDATED_TEXTO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen not equals to DEFAULT_TEXTO_ORIGEN
        defaultTraduccionShouldNotBeFound("textoOrigen.notEquals=" + DEFAULT_TEXTO_ORIGEN);

        // Get all the traduccionList where textoOrigen not equals to UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldBeFound("textoOrigen.notEquals=" + UPDATED_TEXTO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenIsInShouldWork() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen in DEFAULT_TEXTO_ORIGEN or UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldBeFound("textoOrigen.in=" + DEFAULT_TEXTO_ORIGEN + "," + UPDATED_TEXTO_ORIGEN);

        // Get all the traduccionList where textoOrigen equals to UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldNotBeFound("textoOrigen.in=" + UPDATED_TEXTO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenIsNullOrNotNull() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen is not null
        defaultTraduccionShouldBeFound("textoOrigen.specified=true");

        // Get all the traduccionList where textoOrigen is null
        defaultTraduccionShouldNotBeFound("textoOrigen.specified=false");
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenContainsSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen contains DEFAULT_TEXTO_ORIGEN
        defaultTraduccionShouldBeFound("textoOrigen.contains=" + DEFAULT_TEXTO_ORIGEN);

        // Get all the traduccionList where textoOrigen contains UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldNotBeFound("textoOrigen.contains=" + UPDATED_TEXTO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoOrigenNotContainsSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoOrigen does not contain DEFAULT_TEXTO_ORIGEN
        defaultTraduccionShouldNotBeFound("textoOrigen.doesNotContain=" + DEFAULT_TEXTO_ORIGEN);

        // Get all the traduccionList where textoOrigen does not contain UPDATED_TEXTO_ORIGEN
        defaultTraduccionShouldBeFound("textoOrigen.doesNotContain=" + UPDATED_TEXTO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino equals to DEFAULT_TEXTO_DESTINO
        defaultTraduccionShouldBeFound("textoDestino.equals=" + DEFAULT_TEXTO_DESTINO);

        // Get all the traduccionList where textoDestino equals to UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldNotBeFound("textoDestino.equals=" + UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino not equals to DEFAULT_TEXTO_DESTINO
        defaultTraduccionShouldNotBeFound("textoDestino.notEquals=" + DEFAULT_TEXTO_DESTINO);

        // Get all the traduccionList where textoDestino not equals to UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldBeFound("textoDestino.notEquals=" + UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoIsInShouldWork() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino in DEFAULT_TEXTO_DESTINO or UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldBeFound("textoDestino.in=" + DEFAULT_TEXTO_DESTINO + "," + UPDATED_TEXTO_DESTINO);

        // Get all the traduccionList where textoDestino equals to UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldNotBeFound("textoDestino.in=" + UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoIsNullOrNotNull() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino is not null
        defaultTraduccionShouldBeFound("textoDestino.specified=true");

        // Get all the traduccionList where textoDestino is null
        defaultTraduccionShouldNotBeFound("textoDestino.specified=false");
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoContainsSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino contains DEFAULT_TEXTO_DESTINO
        defaultTraduccionShouldBeFound("textoDestino.contains=" + DEFAULT_TEXTO_DESTINO);

        // Get all the traduccionList where textoDestino contains UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldNotBeFound("textoDestino.contains=" + UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void getAllTraduccionsByTextoDestinoNotContainsSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        // Get all the traduccionList where textoDestino does not contain DEFAULT_TEXTO_DESTINO
        defaultTraduccionShouldNotBeFound("textoDestino.doesNotContain=" + DEFAULT_TEXTO_DESTINO);

        // Get all the traduccionList where textoDestino does not contain UPDATED_TEXTO_DESTINO
        defaultTraduccionShouldBeFound("textoDestino.doesNotContain=" + UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void getAllTraduccionsByIdiomaOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);
        Idioma idiomaOrigen;
        if (TestUtil.findAll(em, Idioma.class).isEmpty()) {
            idiomaOrigen = IdiomaResourceIT.createEntity(em);
            em.persist(idiomaOrigen);
            em.flush();
        } else {
            idiomaOrigen = TestUtil.findAll(em, Idioma.class).get(0);
        }
        em.persist(idiomaOrigen);
        em.flush();
        traduccion.setIdiomaOrigen(idiomaOrigen);
        traduccionRepository.saveAndFlush(traduccion);
        Long idiomaOrigenId = idiomaOrigen.getId();

        // Get all the traduccionList where idiomaOrigen equals to idiomaOrigenId
        defaultTraduccionShouldBeFound("idiomaOrigenId.equals=" + idiomaOrigenId);

        // Get all the traduccionList where idiomaOrigen equals to (idiomaOrigenId + 1)
        defaultTraduccionShouldNotBeFound("idiomaOrigenId.equals=" + (idiomaOrigenId + 1));
    }

    @Test
    @Transactional
    void getAllTraduccionsByIdiomaDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);
        Idioma idiomaDestino;
        if (TestUtil.findAll(em, Idioma.class).isEmpty()) {
            idiomaDestino = IdiomaResourceIT.createEntity(em);
            em.persist(idiomaDestino);
            em.flush();
        } else {
            idiomaDestino = TestUtil.findAll(em, Idioma.class).get(0);
        }
        em.persist(idiomaDestino);
        em.flush();
        traduccion.setIdiomaDestino(idiomaDestino);
        traduccionRepository.saveAndFlush(traduccion);
        Long idiomaDestinoId = idiomaDestino.getId();

        // Get all the traduccionList where idiomaDestino equals to idiomaDestinoId
        defaultTraduccionShouldBeFound("idiomaDestinoId.equals=" + idiomaDestinoId);

        // Get all the traduccionList where idiomaDestino equals to (idiomaDestinoId + 1)
        defaultTraduccionShouldNotBeFound("idiomaDestinoId.equals=" + (idiomaDestinoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTraduccionShouldBeFound(String filter) throws Exception {
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traduccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].textoOrigen").value(hasItem(DEFAULT_TEXTO_ORIGEN)))
            .andExpect(jsonPath("$.[*].textoDestino").value(hasItem(DEFAULT_TEXTO_DESTINO)));

        // Check, that the count call also returns 1
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTraduccionShouldNotBeFound(String filter) throws Exception {
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTraduccionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTraduccion() throws Exception {
        // Get the traduccion
        restTraduccionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraduccion() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();

        // Update the traduccion
        Traduccion updatedTraduccion = traduccionRepository.findById(traduccion.getId()).get();
        // Disconnect from session so that the updates on updatedTraduccion are not directly saved in db
        em.detach(updatedTraduccion);
        updatedTraduccion.textoOrigen(UPDATED_TEXTO_ORIGEN).textoDestino(UPDATED_TEXTO_DESTINO);
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(updatedTraduccion);

        restTraduccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traduccionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
        Traduccion testTraduccion = traduccionList.get(traduccionList.size() - 1);
        assertThat(testTraduccion.getTextoOrigen()).isEqualTo(UPDATED_TEXTO_ORIGEN);
        assertThat(testTraduccion.getTextoDestino()).isEqualTo(UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void putNonExistingTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traduccionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traduccionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTraduccionWithPatch() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();

        // Update the traduccion using partial update
        Traduccion partialUpdatedTraduccion = new Traduccion();
        partialUpdatedTraduccion.setId(traduccion.getId());

        partialUpdatedTraduccion.textoOrigen(UPDATED_TEXTO_ORIGEN);

        restTraduccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraduccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraduccion))
            )
            .andExpect(status().isOk());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
        Traduccion testTraduccion = traduccionList.get(traduccionList.size() - 1);
        assertThat(testTraduccion.getTextoOrigen()).isEqualTo(UPDATED_TEXTO_ORIGEN);
        assertThat(testTraduccion.getTextoDestino()).isEqualTo(DEFAULT_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void fullUpdateTraduccionWithPatch() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();

        // Update the traduccion using partial update
        Traduccion partialUpdatedTraduccion = new Traduccion();
        partialUpdatedTraduccion.setId(traduccion.getId());

        partialUpdatedTraduccion.textoOrigen(UPDATED_TEXTO_ORIGEN).textoDestino(UPDATED_TEXTO_DESTINO);

        restTraduccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraduccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraduccion))
            )
            .andExpect(status().isOk());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
        Traduccion testTraduccion = traduccionList.get(traduccionList.size() - 1);
        assertThat(testTraduccion.getTextoOrigen()).isEqualTo(UPDATED_TEXTO_ORIGEN);
        assertThat(testTraduccion.getTextoDestino()).isEqualTo(UPDATED_TEXTO_DESTINO);
    }

    @Test
    @Transactional
    void patchNonExistingTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, traduccionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraduccion() throws Exception {
        int databaseSizeBeforeUpdate = traduccionRepository.findAll().size();
        traduccion.setId(count.incrementAndGet());

        // Create the Traduccion
        TraduccionDTO traduccionDTO = traduccionMapper.toDto(traduccion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraduccionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(traduccionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traduccion in the database
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraduccion() throws Exception {
        // Initialize the database
        traduccionRepository.saveAndFlush(traduccion);

        int databaseSizeBeforeDelete = traduccionRepository.findAll().size();

        // Delete the traduccion
        restTraduccionMockMvc
            .perform(delete(ENTITY_API_URL_ID, traduccion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Traduccion> traduccionList = traduccionRepository.findAll();
        assertThat(traduccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
