package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Clasificaciones;
import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.repository.ClasificacionesRepository;
import com.santi.diccionario.service.ClasificacionesService;
import com.santi.diccionario.service.criteria.ClasificacionesCriteria;
import com.santi.diccionario.service.dto.ClasificacionesDTO;
import com.santi.diccionario.service.mapper.ClasificacionesMapper;
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
 * Integration tests for the {@link ClasificacionesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClasificacionesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_REMITENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_PROVEEDOR = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ARANCELARIO_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ARANCELARIO_ORIGEN = "BBBBBBBBBB";

    private static final Double DEFAULT_IMPORTE = 1D;
    private static final Double UPDATED_IMPORTE = 2D;
    private static final Double SMALLER_IMPORTE = 1D - 1D;

    private static final Integer DEFAULT_UDS = 1;
    private static final Integer UPDATED_UDS = 2;
    private static final Integer SMALLER_UDS = 1 - 1;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;
    private static final Double SMALLER_PESO = 1D - 1D;

    private static final String DEFAULT_CODIGO_ARANCELARIO_OBTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ARANCELARIO_OBTENIDO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clasificaciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClasificacionesRepository clasificacionesRepository;

    @Mock
    private ClasificacionesRepository clasificacionesRepositoryMock;

    @Autowired
    private ClasificacionesMapper clasificacionesMapper;

    @Mock
    private ClasificacionesService clasificacionesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasificacionesMockMvc;

    private Clasificaciones clasificaciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clasificaciones createEntity(EntityManager em) {
        Clasificaciones clasificaciones = new Clasificaciones()
            .descripcion(DEFAULT_DESCRIPCION)
            .cliente(DEFAULT_CLIENTE)
            .remitente(DEFAULT_REMITENTE)
            .destinatario(DEFAULT_DESTINATARIO)
            .proveedor(DEFAULT_PROVEEDOR)
            .codigoArancelarioOrigen(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)
            .importe(DEFAULT_IMPORTE)
            .uds(DEFAULT_UDS)
            .peso(DEFAULT_PESO)
            .codigoArancelarioObtenido(DEFAULT_CODIGO_ARANCELARIO_OBTENIDO);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        clasificaciones.setPaisOrigen(pais);
        // Add required entity
        clasificaciones.setPaisDestino(pais);
        return clasificaciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clasificaciones createUpdatedEntity(EntityManager em) {
        Clasificaciones clasificaciones = new Clasificaciones()
            .descripcion(UPDATED_DESCRIPCION)
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .importe(UPDATED_IMPORTE)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO)
            .codigoArancelarioObtenido(UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createUpdatedEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        clasificaciones.setPaisOrigen(pais);
        // Add required entity
        clasificaciones.setPaisDestino(pais);
        return clasificaciones;
    }

    @BeforeEach
    public void initTest() {
        clasificaciones = createEntity(em);
    }

    @Test
    @Transactional
    void getAllClasificaciones() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].remitente").value(hasItem(DEFAULT_REMITENTE)))
            .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].codigoArancelarioOrigen").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].uds").value(hasItem(DEFAULT_UDS)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].codigoArancelarioObtenido").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_OBTENIDO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasificacionesWithEagerRelationshipsIsEnabled() throws Exception {
        when(clasificacionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasificacionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clasificacionesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasificacionesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clasificacionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasificacionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clasificacionesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getClasificaciones() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get the clasificaciones
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL_ID, clasificaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clasificaciones.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.remitente").value(DEFAULT_REMITENTE))
            .andExpect(jsonPath("$.destinatario").value(DEFAULT_DESTINATARIO))
            .andExpect(jsonPath("$.proveedor").value(DEFAULT_PROVEEDOR))
            .andExpect(jsonPath("$.codigoArancelarioOrigen").value(DEFAULT_CODIGO_ARANCELARIO_ORIGEN))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.doubleValue()))
            .andExpect(jsonPath("$.uds").value(DEFAULT_UDS))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.codigoArancelarioObtenido").value(DEFAULT_CODIGO_ARANCELARIO_OBTENIDO));
    }

    @Test
    @Transactional
    void getClasificacionesByIdFiltering() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        Long id = clasificaciones.getId();

        defaultClasificacionesShouldBeFound("id.equals=" + id);
        defaultClasificacionesShouldNotBeFound("id.notEquals=" + id);

        defaultClasificacionesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClasificacionesShouldNotBeFound("id.greaterThan=" + id);

        defaultClasificacionesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClasificacionesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion equals to DEFAULT_DESCRIPCION
        defaultClasificacionesShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the clasificacionesList where descripcion equals to UPDATED_DESCRIPCION
        defaultClasificacionesShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultClasificacionesShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the clasificacionesList where descripcion not equals to UPDATED_DESCRIPCION
        defaultClasificacionesShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultClasificacionesShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the clasificacionesList where descripcion equals to UPDATED_DESCRIPCION
        defaultClasificacionesShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion is not null
        defaultClasificacionesShouldBeFound("descripcion.specified=true");

        // Get all the clasificacionesList where descripcion is null
        defaultClasificacionesShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion contains DEFAULT_DESCRIPCION
        defaultClasificacionesShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the clasificacionesList where descripcion contains UPDATED_DESCRIPCION
        defaultClasificacionesShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultClasificacionesShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the clasificacionesList where descripcion does not contain UPDATED_DESCRIPCION
        defaultClasificacionesShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente equals to DEFAULT_CLIENTE
        defaultClasificacionesShouldBeFound("cliente.equals=" + DEFAULT_CLIENTE);

        // Get all the clasificacionesList where cliente equals to UPDATED_CLIENTE
        defaultClasificacionesShouldNotBeFound("cliente.equals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente not equals to DEFAULT_CLIENTE
        defaultClasificacionesShouldNotBeFound("cliente.notEquals=" + DEFAULT_CLIENTE);

        // Get all the clasificacionesList where cliente not equals to UPDATED_CLIENTE
        defaultClasificacionesShouldBeFound("cliente.notEquals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente in DEFAULT_CLIENTE or UPDATED_CLIENTE
        defaultClasificacionesShouldBeFound("cliente.in=" + DEFAULT_CLIENTE + "," + UPDATED_CLIENTE);

        // Get all the clasificacionesList where cliente equals to UPDATED_CLIENTE
        defaultClasificacionesShouldNotBeFound("cliente.in=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente is not null
        defaultClasificacionesShouldBeFound("cliente.specified=true");

        // Get all the clasificacionesList where cliente is null
        defaultClasificacionesShouldNotBeFound("cliente.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente contains DEFAULT_CLIENTE
        defaultClasificacionesShouldBeFound("cliente.contains=" + DEFAULT_CLIENTE);

        // Get all the clasificacionesList where cliente contains UPDATED_CLIENTE
        defaultClasificacionesShouldNotBeFound("cliente.contains=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByClienteNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where cliente does not contain DEFAULT_CLIENTE
        defaultClasificacionesShouldNotBeFound("cliente.doesNotContain=" + DEFAULT_CLIENTE);

        // Get all the clasificacionesList where cliente does not contain UPDATED_CLIENTE
        defaultClasificacionesShouldBeFound("cliente.doesNotContain=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente equals to DEFAULT_REMITENTE
        defaultClasificacionesShouldBeFound("remitente.equals=" + DEFAULT_REMITENTE);

        // Get all the clasificacionesList where remitente equals to UPDATED_REMITENTE
        defaultClasificacionesShouldNotBeFound("remitente.equals=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente not equals to DEFAULT_REMITENTE
        defaultClasificacionesShouldNotBeFound("remitente.notEquals=" + DEFAULT_REMITENTE);

        // Get all the clasificacionesList where remitente not equals to UPDATED_REMITENTE
        defaultClasificacionesShouldBeFound("remitente.notEquals=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente in DEFAULT_REMITENTE or UPDATED_REMITENTE
        defaultClasificacionesShouldBeFound("remitente.in=" + DEFAULT_REMITENTE + "," + UPDATED_REMITENTE);

        // Get all the clasificacionesList where remitente equals to UPDATED_REMITENTE
        defaultClasificacionesShouldNotBeFound("remitente.in=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente is not null
        defaultClasificacionesShouldBeFound("remitente.specified=true");

        // Get all the clasificacionesList where remitente is null
        defaultClasificacionesShouldNotBeFound("remitente.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente contains DEFAULT_REMITENTE
        defaultClasificacionesShouldBeFound("remitente.contains=" + DEFAULT_REMITENTE);

        // Get all the clasificacionesList where remitente contains UPDATED_REMITENTE
        defaultClasificacionesShouldNotBeFound("remitente.contains=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByRemitenteNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where remitente does not contain DEFAULT_REMITENTE
        defaultClasificacionesShouldNotBeFound("remitente.doesNotContain=" + DEFAULT_REMITENTE);

        // Get all the clasificacionesList where remitente does not contain UPDATED_REMITENTE
        defaultClasificacionesShouldBeFound("remitente.doesNotContain=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario equals to DEFAULT_DESTINATARIO
        defaultClasificacionesShouldBeFound("destinatario.equals=" + DEFAULT_DESTINATARIO);

        // Get all the clasificacionesList where destinatario equals to UPDATED_DESTINATARIO
        defaultClasificacionesShouldNotBeFound("destinatario.equals=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario not equals to DEFAULT_DESTINATARIO
        defaultClasificacionesShouldNotBeFound("destinatario.notEquals=" + DEFAULT_DESTINATARIO);

        // Get all the clasificacionesList where destinatario not equals to UPDATED_DESTINATARIO
        defaultClasificacionesShouldBeFound("destinatario.notEquals=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario in DEFAULT_DESTINATARIO or UPDATED_DESTINATARIO
        defaultClasificacionesShouldBeFound("destinatario.in=" + DEFAULT_DESTINATARIO + "," + UPDATED_DESTINATARIO);

        // Get all the clasificacionesList where destinatario equals to UPDATED_DESTINATARIO
        defaultClasificacionesShouldNotBeFound("destinatario.in=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario is not null
        defaultClasificacionesShouldBeFound("destinatario.specified=true");

        // Get all the clasificacionesList where destinatario is null
        defaultClasificacionesShouldNotBeFound("destinatario.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario contains DEFAULT_DESTINATARIO
        defaultClasificacionesShouldBeFound("destinatario.contains=" + DEFAULT_DESTINATARIO);

        // Get all the clasificacionesList where destinatario contains UPDATED_DESTINATARIO
        defaultClasificacionesShouldNotBeFound("destinatario.contains=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByDestinatarioNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where destinatario does not contain DEFAULT_DESTINATARIO
        defaultClasificacionesShouldNotBeFound("destinatario.doesNotContain=" + DEFAULT_DESTINATARIO);

        // Get all the clasificacionesList where destinatario does not contain UPDATED_DESTINATARIO
        defaultClasificacionesShouldBeFound("destinatario.doesNotContain=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor equals to DEFAULT_PROVEEDOR
        defaultClasificacionesShouldBeFound("proveedor.equals=" + DEFAULT_PROVEEDOR);

        // Get all the clasificacionesList where proveedor equals to UPDATED_PROVEEDOR
        defaultClasificacionesShouldNotBeFound("proveedor.equals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor not equals to DEFAULT_PROVEEDOR
        defaultClasificacionesShouldNotBeFound("proveedor.notEquals=" + DEFAULT_PROVEEDOR);

        // Get all the clasificacionesList where proveedor not equals to UPDATED_PROVEEDOR
        defaultClasificacionesShouldBeFound("proveedor.notEquals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor in DEFAULT_PROVEEDOR or UPDATED_PROVEEDOR
        defaultClasificacionesShouldBeFound("proveedor.in=" + DEFAULT_PROVEEDOR + "," + UPDATED_PROVEEDOR);

        // Get all the clasificacionesList where proveedor equals to UPDATED_PROVEEDOR
        defaultClasificacionesShouldNotBeFound("proveedor.in=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor is not null
        defaultClasificacionesShouldBeFound("proveedor.specified=true");

        // Get all the clasificacionesList where proveedor is null
        defaultClasificacionesShouldNotBeFound("proveedor.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor contains DEFAULT_PROVEEDOR
        defaultClasificacionesShouldBeFound("proveedor.contains=" + DEFAULT_PROVEEDOR);

        // Get all the clasificacionesList where proveedor contains UPDATED_PROVEEDOR
        defaultClasificacionesShouldNotBeFound("proveedor.contains=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificacionesByProveedorNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where proveedor does not contain DEFAULT_PROVEEDOR
        defaultClasificacionesShouldNotBeFound("proveedor.doesNotContain=" + DEFAULT_PROVEEDOR);

        // Get all the clasificacionesList where proveedor does not contain UPDATED_PROVEEDOR
        defaultClasificacionesShouldBeFound("proveedor.doesNotContain=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen equals to DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldBeFound("codigoArancelarioOrigen.equals=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificacionesList where codigoArancelarioOrigen equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.equals=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen not equals to DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.notEquals=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificacionesList where codigoArancelarioOrigen not equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldBeFound("codigoArancelarioOrigen.notEquals=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen in DEFAULT_CODIGO_ARANCELARIO_ORIGEN or UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldBeFound(
            "codigoArancelarioOrigen.in=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN + "," + UPDATED_CODIGO_ARANCELARIO_ORIGEN
        );

        // Get all the clasificacionesList where codigoArancelarioOrigen equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.in=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen is not null
        defaultClasificacionesShouldBeFound("codigoArancelarioOrigen.specified=true");

        // Get all the clasificacionesList where codigoArancelarioOrigen is null
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen contains DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldBeFound("codigoArancelarioOrigen.contains=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificacionesList where codigoArancelarioOrigen contains UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.contains=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioOrigenNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioOrigen does not contain DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldNotBeFound("codigoArancelarioOrigen.doesNotContain=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificacionesList where codigoArancelarioOrigen does not contain UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificacionesShouldBeFound("codigoArancelarioOrigen.doesNotContain=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe equals to DEFAULT_IMPORTE
        defaultClasificacionesShouldBeFound("importe.equals=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe equals to UPDATED_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.equals=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe not equals to DEFAULT_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.notEquals=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe not equals to UPDATED_IMPORTE
        defaultClasificacionesShouldBeFound("importe.notEquals=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe in DEFAULT_IMPORTE or UPDATED_IMPORTE
        defaultClasificacionesShouldBeFound("importe.in=" + DEFAULT_IMPORTE + "," + UPDATED_IMPORTE);

        // Get all the clasificacionesList where importe equals to UPDATED_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.in=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe is not null
        defaultClasificacionesShouldBeFound("importe.specified=true");

        // Get all the clasificacionesList where importe is null
        defaultClasificacionesShouldNotBeFound("importe.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe is greater than or equal to DEFAULT_IMPORTE
        defaultClasificacionesShouldBeFound("importe.greaterThanOrEqual=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe is greater than or equal to UPDATED_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.greaterThanOrEqual=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe is less than or equal to DEFAULT_IMPORTE
        defaultClasificacionesShouldBeFound("importe.lessThanOrEqual=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe is less than or equal to SMALLER_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.lessThanOrEqual=" + SMALLER_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe is less than DEFAULT_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.lessThan=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe is less than UPDATED_IMPORTE
        defaultClasificacionesShouldBeFound("importe.lessThan=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByImporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where importe is greater than DEFAULT_IMPORTE
        defaultClasificacionesShouldNotBeFound("importe.greaterThan=" + DEFAULT_IMPORTE);

        // Get all the clasificacionesList where importe is greater than SMALLER_IMPORTE
        defaultClasificacionesShouldBeFound("importe.greaterThan=" + SMALLER_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds equals to DEFAULT_UDS
        defaultClasificacionesShouldBeFound("uds.equals=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds equals to UPDATED_UDS
        defaultClasificacionesShouldNotBeFound("uds.equals=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds not equals to DEFAULT_UDS
        defaultClasificacionesShouldNotBeFound("uds.notEquals=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds not equals to UPDATED_UDS
        defaultClasificacionesShouldBeFound("uds.notEquals=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds in DEFAULT_UDS or UPDATED_UDS
        defaultClasificacionesShouldBeFound("uds.in=" + DEFAULT_UDS + "," + UPDATED_UDS);

        // Get all the clasificacionesList where uds equals to UPDATED_UDS
        defaultClasificacionesShouldNotBeFound("uds.in=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds is not null
        defaultClasificacionesShouldBeFound("uds.specified=true");

        // Get all the clasificacionesList where uds is null
        defaultClasificacionesShouldNotBeFound("uds.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds is greater than or equal to DEFAULT_UDS
        defaultClasificacionesShouldBeFound("uds.greaterThanOrEqual=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds is greater than or equal to UPDATED_UDS
        defaultClasificacionesShouldNotBeFound("uds.greaterThanOrEqual=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds is less than or equal to DEFAULT_UDS
        defaultClasificacionesShouldBeFound("uds.lessThanOrEqual=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds is less than or equal to SMALLER_UDS
        defaultClasificacionesShouldNotBeFound("uds.lessThanOrEqual=" + SMALLER_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds is less than DEFAULT_UDS
        defaultClasificacionesShouldNotBeFound("uds.lessThan=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds is less than UPDATED_UDS
        defaultClasificacionesShouldBeFound("uds.lessThan=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByUdsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where uds is greater than DEFAULT_UDS
        defaultClasificacionesShouldNotBeFound("uds.greaterThan=" + DEFAULT_UDS);

        // Get all the clasificacionesList where uds is greater than SMALLER_UDS
        defaultClasificacionesShouldBeFound("uds.greaterThan=" + SMALLER_UDS);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso equals to DEFAULT_PESO
        defaultClasificacionesShouldBeFound("peso.equals=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso equals to UPDATED_PESO
        defaultClasificacionesShouldNotBeFound("peso.equals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso not equals to DEFAULT_PESO
        defaultClasificacionesShouldNotBeFound("peso.notEquals=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso not equals to UPDATED_PESO
        defaultClasificacionesShouldBeFound("peso.notEquals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso in DEFAULT_PESO or UPDATED_PESO
        defaultClasificacionesShouldBeFound("peso.in=" + DEFAULT_PESO + "," + UPDATED_PESO);

        // Get all the clasificacionesList where peso equals to UPDATED_PESO
        defaultClasificacionesShouldNotBeFound("peso.in=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso is not null
        defaultClasificacionesShouldBeFound("peso.specified=true");

        // Get all the clasificacionesList where peso is null
        defaultClasificacionesShouldNotBeFound("peso.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso is greater than or equal to DEFAULT_PESO
        defaultClasificacionesShouldBeFound("peso.greaterThanOrEqual=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso is greater than or equal to UPDATED_PESO
        defaultClasificacionesShouldNotBeFound("peso.greaterThanOrEqual=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso is less than or equal to DEFAULT_PESO
        defaultClasificacionesShouldBeFound("peso.lessThanOrEqual=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso is less than or equal to SMALLER_PESO
        defaultClasificacionesShouldNotBeFound("peso.lessThanOrEqual=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso is less than DEFAULT_PESO
        defaultClasificacionesShouldNotBeFound("peso.lessThan=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso is less than UPDATED_PESO
        defaultClasificacionesShouldBeFound("peso.lessThan=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPesoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where peso is greater than DEFAULT_PESO
        defaultClasificacionesShouldNotBeFound("peso.greaterThan=" + DEFAULT_PESO);

        // Get all the clasificacionesList where peso is greater than SMALLER_PESO
        defaultClasificacionesShouldBeFound("peso.greaterThan=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido equals to DEFAULT_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldBeFound("codigoArancelarioObtenido.equals=" + DEFAULT_CODIGO_ARANCELARIO_OBTENIDO);

        // Get all the clasificacionesList where codigoArancelarioObtenido equals to UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.equals=" + UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido not equals to DEFAULT_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.notEquals=" + DEFAULT_CODIGO_ARANCELARIO_OBTENIDO);

        // Get all the clasificacionesList where codigoArancelarioObtenido not equals to UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldBeFound("codigoArancelarioObtenido.notEquals=" + UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoIsInShouldWork() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido in DEFAULT_CODIGO_ARANCELARIO_OBTENIDO or UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldBeFound(
            "codigoArancelarioObtenido.in=" + DEFAULT_CODIGO_ARANCELARIO_OBTENIDO + "," + UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        );

        // Get all the clasificacionesList where codigoArancelarioObtenido equals to UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.in=" + UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido is not null
        defaultClasificacionesShouldBeFound("codigoArancelarioObtenido.specified=true");

        // Get all the clasificacionesList where codigoArancelarioObtenido is null
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido contains DEFAULT_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldBeFound("codigoArancelarioObtenido.contains=" + DEFAULT_CODIGO_ARANCELARIO_OBTENIDO);

        // Get all the clasificacionesList where codigoArancelarioObtenido contains UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.contains=" + UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByCodigoArancelarioObtenidoNotContainsSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);

        // Get all the clasificacionesList where codigoArancelarioObtenido does not contain DEFAULT_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldNotBeFound("codigoArancelarioObtenido.doesNotContain=" + DEFAULT_CODIGO_ARANCELARIO_OBTENIDO);

        // Get all the clasificacionesList where codigoArancelarioObtenido does not contain UPDATED_CODIGO_ARANCELARIO_OBTENIDO
        defaultClasificacionesShouldBeFound("codigoArancelarioObtenido.doesNotContain=" + UPDATED_CODIGO_ARANCELARIO_OBTENIDO);
    }

    @Test
    @Transactional
    void getAllClasificacionesByPaisOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Pais paisOrigen;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            paisOrigen = PaisResourceIT.createEntity(em);
            em.persist(paisOrigen);
            em.flush();
        } else {
            paisOrigen = TestUtil.findAll(em, Pais.class).get(0);
        }
        em.persist(paisOrigen);
        em.flush();
        clasificaciones.setPaisOrigen(paisOrigen);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long paisOrigenId = paisOrigen.getId();

        // Get all the clasificacionesList where paisOrigen equals to paisOrigenId
        defaultClasificacionesShouldBeFound("paisOrigenId.equals=" + paisOrigenId);

        // Get all the clasificacionesList where paisOrigen equals to (paisOrigenId + 1)
        defaultClasificacionesShouldNotBeFound("paisOrigenId.equals=" + (paisOrigenId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByPaisDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Pais paisDestino;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            paisDestino = PaisResourceIT.createEntity(em);
            em.persist(paisDestino);
            em.flush();
        } else {
            paisDestino = TestUtil.findAll(em, Pais.class).get(0);
        }
        em.persist(paisDestino);
        em.flush();
        clasificaciones.setPaisDestino(paisDestino);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long paisDestinoId = paisDestino.getId();

        // Get all the clasificacionesList where paisDestino equals to paisDestinoId
        defaultClasificacionesShouldBeFound("paisDestinoId.equals=" + paisDestinoId);

        // Get all the clasificacionesList where paisDestino equals to (paisDestinoId + 1)
        defaultClasificacionesShouldNotBeFound("paisDestinoId.equals=" + (paisDestinoId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByDivisaIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Divisa divisa;
        if (TestUtil.findAll(em, Divisa.class).isEmpty()) {
            divisa = DivisaResourceIT.createEntity(em);
            em.persist(divisa);
            em.flush();
        } else {
            divisa = TestUtil.findAll(em, Divisa.class).get(0);
        }
        em.persist(divisa);
        em.flush();
        clasificaciones.setDivisa(divisa);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long divisaId = divisa.getId();

        // Get all the clasificacionesList where divisa equals to divisaId
        defaultClasificacionesShouldBeFound("divisaId.equals=" + divisaId);

        // Get all the clasificacionesList where divisa equals to (divisaId + 1)
        defaultClasificacionesShouldNotBeFound("divisaId.equals=" + (divisaId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByIdiomaIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Idioma idioma;
        if (TestUtil.findAll(em, Idioma.class).isEmpty()) {
            idioma = IdiomaResourceIT.createEntity(em);
            em.persist(idioma);
            em.flush();
        } else {
            idioma = TestUtil.findAll(em, Idioma.class).get(0);
        }
        em.persist(idioma);
        em.flush();
        clasificaciones.setIdioma(idioma);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long idiomaId = idioma.getId();

        // Get all the clasificacionesList where idioma equals to idiomaId
        defaultClasificacionesShouldBeFound("idiomaId.equals=" + idiomaId);

        // Get all the clasificacionesList where idioma equals to (idiomaId + 1)
        defaultClasificacionesShouldNotBeFound("idiomaId.equals=" + (idiomaId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByRefClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Cliente refCliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            refCliente = ClienteResourceIT.createEntity(em);
            em.persist(refCliente);
            em.flush();
        } else {
            refCliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        em.persist(refCliente);
        em.flush();
        clasificaciones.setRefCliente(refCliente);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long refClienteId = refCliente.getId();

        // Get all the clasificacionesList where refCliente equals to refClienteId
        defaultClasificacionesShouldBeFound("refClienteId.equals=" + refClienteId);

        // Get all the clasificacionesList where refCliente equals to (refClienteId + 1)
        defaultClasificacionesShouldNotBeFound("refClienteId.equals=" + (refClienteId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByProvinciaDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Provincia provinciaDestino;
        if (TestUtil.findAll(em, Provincia.class).isEmpty()) {
            provinciaDestino = ProvinciaResourceIT.createEntity(em);
            em.persist(provinciaDestino);
            em.flush();
        } else {
            provinciaDestino = TestUtil.findAll(em, Provincia.class).get(0);
        }
        em.persist(provinciaDestino);
        em.flush();
        clasificaciones.setProvinciaDestino(provinciaDestino);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long provinciaDestinoId = provinciaDestino.getId();

        // Get all the clasificacionesList where provinciaDestino equals to provinciaDestinoId
        defaultClasificacionesShouldBeFound("provinciaDestinoId.equals=" + provinciaDestinoId);

        // Get all the clasificacionesList where provinciaDestino equals to (provinciaDestinoId + 1)
        defaultClasificacionesShouldNotBeFound("provinciaDestinoId.equals=" + (provinciaDestinoId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByIdRemitenteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Remitente idRemitente;
        if (TestUtil.findAll(em, Remitente.class).isEmpty()) {
            idRemitente = RemitenteResourceIT.createEntity(em);
            em.persist(idRemitente);
            em.flush();
        } else {
            idRemitente = TestUtil.findAll(em, Remitente.class).get(0);
        }
        em.persist(idRemitente);
        em.flush();
        clasificaciones.setIdRemitente(idRemitente);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long idRemitenteId = idRemitente.getId();

        // Get all the clasificacionesList where idRemitente equals to idRemitenteId
        defaultClasificacionesShouldBeFound("idRemitenteId.equals=" + idRemitenteId);

        // Get all the clasificacionesList where idRemitente equals to (idRemitenteId + 1)
        defaultClasificacionesShouldNotBeFound("idRemitenteId.equals=" + (idRemitenteId + 1));
    }

    @Test
    @Transactional
    void getAllClasificacionesByIdDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Destinatario idDestinatario;
        if (TestUtil.findAll(em, Destinatario.class).isEmpty()) {
            idDestinatario = DestinatarioResourceIT.createEntity(em);
            em.persist(idDestinatario);
            em.flush();
        } else {
            idDestinatario = TestUtil.findAll(em, Destinatario.class).get(0);
        }
        em.persist(idDestinatario);
        em.flush();
        clasificaciones.setIdDestinatario(idDestinatario);
        clasificacionesRepository.saveAndFlush(clasificaciones);
        Long idDestinatarioId = idDestinatario.getId();

        // Get all the clasificacionesList where idDestinatario equals to idDestinatarioId
        defaultClasificacionesShouldBeFound("idDestinatarioId.equals=" + idDestinatarioId);

        // Get all the clasificacionesList where idDestinatario equals to (idDestinatarioId + 1)
        defaultClasificacionesShouldNotBeFound("idDestinatarioId.equals=" + (idDestinatarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClasificacionesShouldBeFound(String filter) throws Exception {
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].remitente").value(hasItem(DEFAULT_REMITENTE)))
            .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].codigoArancelarioOrigen").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].uds").value(hasItem(DEFAULT_UDS)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].codigoArancelarioObtenido").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_OBTENIDO)));

        // Check, that the count call also returns 1
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClasificacionesShouldNotBeFound(String filter) throws Exception {
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClasificacionesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClasificaciones() throws Exception {
        // Get the clasificaciones
        restClasificacionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
