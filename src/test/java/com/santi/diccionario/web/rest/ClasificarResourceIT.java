package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Clasificar;
import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.Provincia;
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.repository.ClasificarRepository;
import com.santi.diccionario.service.ClasificarService;
import com.santi.diccionario.service.criteria.ClasificarCriteria;
import com.santi.diccionario.service.dto.ClasificarDTO;
import com.santi.diccionario.service.mapper.ClasificarMapper;
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
 * Integration tests for the {@link ClasificarResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClasificarResourceIT {

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

    private static final String ENTITY_API_URL = "/api/clasificars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClasificarRepository clasificarRepository;

    @Mock
    private ClasificarRepository clasificarRepositoryMock;

    @Autowired
    private ClasificarMapper clasificarMapper;

    @Mock
    private ClasificarService clasificarServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasificarMockMvc;

    private Clasificar clasificar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clasificar createEntity(EntityManager em) {
        Clasificar clasificar = new Clasificar()
            .descripcion(DEFAULT_DESCRIPCION)
            .cliente(DEFAULT_CLIENTE)
            .remitente(DEFAULT_REMITENTE)
            .destinatario(DEFAULT_DESTINATARIO)
            .proveedor(DEFAULT_PROVEEDOR)
            .codigoArancelarioOrigen(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)
            .importe(DEFAULT_IMPORTE)
            .uds(DEFAULT_UDS)
            .peso(DEFAULT_PESO);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        clasificar.setPaisOrigen(pais);
        // Add required entity
        clasificar.setPaisDestino(pais);
        return clasificar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clasificar createUpdatedEntity(EntityManager em) {
        Clasificar clasificar = new Clasificar()
            .descripcion(UPDATED_DESCRIPCION)
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .importe(UPDATED_IMPORTE)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createUpdatedEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        clasificar.setPaisOrigen(pais);
        // Add required entity
        clasificar.setPaisDestino(pais);
        return clasificar;
    }

    @BeforeEach
    public void initTest() {
        clasificar = createEntity(em);
    }

    @Test
    @Transactional
    void createClasificar() throws Exception {
        int databaseSizeBeforeCreate = clasificarRepository.findAll().size();
        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);
        restClasificarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clasificarDTO)))
            .andExpect(status().isCreated());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeCreate + 1);
        Clasificar testClasificar = clasificarList.get(clasificarList.size() - 1);
        assertThat(testClasificar.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testClasificar.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testClasificar.getRemitente()).isEqualTo(DEFAULT_REMITENTE);
        assertThat(testClasificar.getDestinatario()).isEqualTo(DEFAULT_DESTINATARIO);
        assertThat(testClasificar.getProveedor()).isEqualTo(DEFAULT_PROVEEDOR);
        assertThat(testClasificar.getCodigoArancelarioOrigen()).isEqualTo(DEFAULT_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testClasificar.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testClasificar.getUds()).isEqualTo(DEFAULT_UDS);
        assertThat(testClasificar.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void createClasificarWithExistingId() throws Exception {
        // Create the Clasificar with an existing ID
        clasificar.setId(1L);
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        int databaseSizeBeforeCreate = clasificarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasificarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clasificarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificarRepository.findAll().size();
        // set the field null
        clasificar.setDescripcion(null);

        // Create the Clasificar, which fails.
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        restClasificarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clasificarDTO)))
            .andExpect(status().isBadRequest());

        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClasificars() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].remitente").value(hasItem(DEFAULT_REMITENTE)))
            .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].codigoArancelarioOrigen").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].uds").value(hasItem(DEFAULT_UDS)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasificarsWithEagerRelationshipsIsEnabled() throws Exception {
        when(clasificarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasificarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clasificarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClasificarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clasificarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClasificarMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clasificarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getClasificar() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get the clasificar
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL_ID, clasificar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clasificar.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.remitente").value(DEFAULT_REMITENTE))
            .andExpect(jsonPath("$.destinatario").value(DEFAULT_DESTINATARIO))
            .andExpect(jsonPath("$.proveedor").value(DEFAULT_PROVEEDOR))
            .andExpect(jsonPath("$.codigoArancelarioOrigen").value(DEFAULT_CODIGO_ARANCELARIO_ORIGEN))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.doubleValue()))
            .andExpect(jsonPath("$.uds").value(DEFAULT_UDS))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()));
    }

    @Test
    @Transactional
    void getClasificarsByIdFiltering() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        Long id = clasificar.getId();

        defaultClasificarShouldBeFound("id.equals=" + id);
        defaultClasificarShouldNotBeFound("id.notEquals=" + id);

        defaultClasificarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClasificarShouldNotBeFound("id.greaterThan=" + id);

        defaultClasificarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClasificarShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion equals to DEFAULT_DESCRIPCION
        defaultClasificarShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the clasificarList where descripcion equals to UPDATED_DESCRIPCION
        defaultClasificarShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultClasificarShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the clasificarList where descripcion not equals to UPDATED_DESCRIPCION
        defaultClasificarShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultClasificarShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the clasificarList where descripcion equals to UPDATED_DESCRIPCION
        defaultClasificarShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion is not null
        defaultClasificarShouldBeFound("descripcion.specified=true");

        // Get all the clasificarList where descripcion is null
        defaultClasificarShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion contains DEFAULT_DESCRIPCION
        defaultClasificarShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the clasificarList where descripcion contains UPDATED_DESCRIPCION
        defaultClasificarShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificarsByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultClasificarShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the clasificarList where descripcion does not contain UPDATED_DESCRIPCION
        defaultClasificarShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente equals to DEFAULT_CLIENTE
        defaultClasificarShouldBeFound("cliente.equals=" + DEFAULT_CLIENTE);

        // Get all the clasificarList where cliente equals to UPDATED_CLIENTE
        defaultClasificarShouldNotBeFound("cliente.equals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente not equals to DEFAULT_CLIENTE
        defaultClasificarShouldNotBeFound("cliente.notEquals=" + DEFAULT_CLIENTE);

        // Get all the clasificarList where cliente not equals to UPDATED_CLIENTE
        defaultClasificarShouldBeFound("cliente.notEquals=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente in DEFAULT_CLIENTE or UPDATED_CLIENTE
        defaultClasificarShouldBeFound("cliente.in=" + DEFAULT_CLIENTE + "," + UPDATED_CLIENTE);

        // Get all the clasificarList where cliente equals to UPDATED_CLIENTE
        defaultClasificarShouldNotBeFound("cliente.in=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente is not null
        defaultClasificarShouldBeFound("cliente.specified=true");

        // Get all the clasificarList where cliente is null
        defaultClasificarShouldNotBeFound("cliente.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente contains DEFAULT_CLIENTE
        defaultClasificarShouldBeFound("cliente.contains=" + DEFAULT_CLIENTE);

        // Get all the clasificarList where cliente contains UPDATED_CLIENTE
        defaultClasificarShouldNotBeFound("cliente.contains=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByClienteNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where cliente does not contain DEFAULT_CLIENTE
        defaultClasificarShouldNotBeFound("cliente.doesNotContain=" + DEFAULT_CLIENTE);

        // Get all the clasificarList where cliente does not contain UPDATED_CLIENTE
        defaultClasificarShouldBeFound("cliente.doesNotContain=" + UPDATED_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente equals to DEFAULT_REMITENTE
        defaultClasificarShouldBeFound("remitente.equals=" + DEFAULT_REMITENTE);

        // Get all the clasificarList where remitente equals to UPDATED_REMITENTE
        defaultClasificarShouldNotBeFound("remitente.equals=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente not equals to DEFAULT_REMITENTE
        defaultClasificarShouldNotBeFound("remitente.notEquals=" + DEFAULT_REMITENTE);

        // Get all the clasificarList where remitente not equals to UPDATED_REMITENTE
        defaultClasificarShouldBeFound("remitente.notEquals=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente in DEFAULT_REMITENTE or UPDATED_REMITENTE
        defaultClasificarShouldBeFound("remitente.in=" + DEFAULT_REMITENTE + "," + UPDATED_REMITENTE);

        // Get all the clasificarList where remitente equals to UPDATED_REMITENTE
        defaultClasificarShouldNotBeFound("remitente.in=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente is not null
        defaultClasificarShouldBeFound("remitente.specified=true");

        // Get all the clasificarList where remitente is null
        defaultClasificarShouldNotBeFound("remitente.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente contains DEFAULT_REMITENTE
        defaultClasificarShouldBeFound("remitente.contains=" + DEFAULT_REMITENTE);

        // Get all the clasificarList where remitente contains UPDATED_REMITENTE
        defaultClasificarShouldNotBeFound("remitente.contains=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByRemitenteNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where remitente does not contain DEFAULT_REMITENTE
        defaultClasificarShouldNotBeFound("remitente.doesNotContain=" + DEFAULT_REMITENTE);

        // Get all the clasificarList where remitente does not contain UPDATED_REMITENTE
        defaultClasificarShouldBeFound("remitente.doesNotContain=" + UPDATED_REMITENTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario equals to DEFAULT_DESTINATARIO
        defaultClasificarShouldBeFound("destinatario.equals=" + DEFAULT_DESTINATARIO);

        // Get all the clasificarList where destinatario equals to UPDATED_DESTINATARIO
        defaultClasificarShouldNotBeFound("destinatario.equals=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario not equals to DEFAULT_DESTINATARIO
        defaultClasificarShouldNotBeFound("destinatario.notEquals=" + DEFAULT_DESTINATARIO);

        // Get all the clasificarList where destinatario not equals to UPDATED_DESTINATARIO
        defaultClasificarShouldBeFound("destinatario.notEquals=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario in DEFAULT_DESTINATARIO or UPDATED_DESTINATARIO
        defaultClasificarShouldBeFound("destinatario.in=" + DEFAULT_DESTINATARIO + "," + UPDATED_DESTINATARIO);

        // Get all the clasificarList where destinatario equals to UPDATED_DESTINATARIO
        defaultClasificarShouldNotBeFound("destinatario.in=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario is not null
        defaultClasificarShouldBeFound("destinatario.specified=true");

        // Get all the clasificarList where destinatario is null
        defaultClasificarShouldNotBeFound("destinatario.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario contains DEFAULT_DESTINATARIO
        defaultClasificarShouldBeFound("destinatario.contains=" + DEFAULT_DESTINATARIO);

        // Get all the clasificarList where destinatario contains UPDATED_DESTINATARIO
        defaultClasificarShouldNotBeFound("destinatario.contains=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificarsByDestinatarioNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where destinatario does not contain DEFAULT_DESTINATARIO
        defaultClasificarShouldNotBeFound("destinatario.doesNotContain=" + DEFAULT_DESTINATARIO);

        // Get all the clasificarList where destinatario does not contain UPDATED_DESTINATARIO
        defaultClasificarShouldBeFound("destinatario.doesNotContain=" + UPDATED_DESTINATARIO);
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor equals to DEFAULT_PROVEEDOR
        defaultClasificarShouldBeFound("proveedor.equals=" + DEFAULT_PROVEEDOR);

        // Get all the clasificarList where proveedor equals to UPDATED_PROVEEDOR
        defaultClasificarShouldNotBeFound("proveedor.equals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor not equals to DEFAULT_PROVEEDOR
        defaultClasificarShouldNotBeFound("proveedor.notEquals=" + DEFAULT_PROVEEDOR);

        // Get all the clasificarList where proveedor not equals to UPDATED_PROVEEDOR
        defaultClasificarShouldBeFound("proveedor.notEquals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor in DEFAULT_PROVEEDOR or UPDATED_PROVEEDOR
        defaultClasificarShouldBeFound("proveedor.in=" + DEFAULT_PROVEEDOR + "," + UPDATED_PROVEEDOR);

        // Get all the clasificarList where proveedor equals to UPDATED_PROVEEDOR
        defaultClasificarShouldNotBeFound("proveedor.in=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor is not null
        defaultClasificarShouldBeFound("proveedor.specified=true");

        // Get all the clasificarList where proveedor is null
        defaultClasificarShouldNotBeFound("proveedor.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor contains DEFAULT_PROVEEDOR
        defaultClasificarShouldBeFound("proveedor.contains=" + DEFAULT_PROVEEDOR);

        // Get all the clasificarList where proveedor contains UPDATED_PROVEEDOR
        defaultClasificarShouldNotBeFound("proveedor.contains=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificarsByProveedorNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where proveedor does not contain DEFAULT_PROVEEDOR
        defaultClasificarShouldNotBeFound("proveedor.doesNotContain=" + DEFAULT_PROVEEDOR);

        // Get all the clasificarList where proveedor does not contain UPDATED_PROVEEDOR
        defaultClasificarShouldBeFound("proveedor.doesNotContain=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen equals to DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldBeFound("codigoArancelarioOrigen.equals=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificarList where codigoArancelarioOrigen equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.equals=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen not equals to DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.notEquals=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificarList where codigoArancelarioOrigen not equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldBeFound("codigoArancelarioOrigen.notEquals=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen in DEFAULT_CODIGO_ARANCELARIO_ORIGEN or UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldBeFound(
            "codigoArancelarioOrigen.in=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN + "," + UPDATED_CODIGO_ARANCELARIO_ORIGEN
        );

        // Get all the clasificarList where codigoArancelarioOrigen equals to UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.in=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen is not null
        defaultClasificarShouldBeFound("codigoArancelarioOrigen.specified=true");

        // Get all the clasificarList where codigoArancelarioOrigen is null
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen contains DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldBeFound("codigoArancelarioOrigen.contains=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificarList where codigoArancelarioOrigen contains UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.contains=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificarsByCodigoArancelarioOrigenNotContainsSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where codigoArancelarioOrigen does not contain DEFAULT_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldNotBeFound("codigoArancelarioOrigen.doesNotContain=" + DEFAULT_CODIGO_ARANCELARIO_ORIGEN);

        // Get all the clasificarList where codigoArancelarioOrigen does not contain UPDATED_CODIGO_ARANCELARIO_ORIGEN
        defaultClasificarShouldBeFound("codigoArancelarioOrigen.doesNotContain=" + UPDATED_CODIGO_ARANCELARIO_ORIGEN);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe equals to DEFAULT_IMPORTE
        defaultClasificarShouldBeFound("importe.equals=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe equals to UPDATED_IMPORTE
        defaultClasificarShouldNotBeFound("importe.equals=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe not equals to DEFAULT_IMPORTE
        defaultClasificarShouldNotBeFound("importe.notEquals=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe not equals to UPDATED_IMPORTE
        defaultClasificarShouldBeFound("importe.notEquals=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe in DEFAULT_IMPORTE or UPDATED_IMPORTE
        defaultClasificarShouldBeFound("importe.in=" + DEFAULT_IMPORTE + "," + UPDATED_IMPORTE);

        // Get all the clasificarList where importe equals to UPDATED_IMPORTE
        defaultClasificarShouldNotBeFound("importe.in=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe is not null
        defaultClasificarShouldBeFound("importe.specified=true");

        // Get all the clasificarList where importe is null
        defaultClasificarShouldNotBeFound("importe.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe is greater than or equal to DEFAULT_IMPORTE
        defaultClasificarShouldBeFound("importe.greaterThanOrEqual=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe is greater than or equal to UPDATED_IMPORTE
        defaultClasificarShouldNotBeFound("importe.greaterThanOrEqual=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe is less than or equal to DEFAULT_IMPORTE
        defaultClasificarShouldBeFound("importe.lessThanOrEqual=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe is less than or equal to SMALLER_IMPORTE
        defaultClasificarShouldNotBeFound("importe.lessThanOrEqual=" + SMALLER_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe is less than DEFAULT_IMPORTE
        defaultClasificarShouldNotBeFound("importe.lessThan=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe is less than UPDATED_IMPORTE
        defaultClasificarShouldBeFound("importe.lessThan=" + UPDATED_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByImporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where importe is greater than DEFAULT_IMPORTE
        defaultClasificarShouldNotBeFound("importe.greaterThan=" + DEFAULT_IMPORTE);

        // Get all the clasificarList where importe is greater than SMALLER_IMPORTE
        defaultClasificarShouldBeFound("importe.greaterThan=" + SMALLER_IMPORTE);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds equals to DEFAULT_UDS
        defaultClasificarShouldBeFound("uds.equals=" + DEFAULT_UDS);

        // Get all the clasificarList where uds equals to UPDATED_UDS
        defaultClasificarShouldNotBeFound("uds.equals=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds not equals to DEFAULT_UDS
        defaultClasificarShouldNotBeFound("uds.notEquals=" + DEFAULT_UDS);

        // Get all the clasificarList where uds not equals to UPDATED_UDS
        defaultClasificarShouldBeFound("uds.notEquals=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds in DEFAULT_UDS or UPDATED_UDS
        defaultClasificarShouldBeFound("uds.in=" + DEFAULT_UDS + "," + UPDATED_UDS);

        // Get all the clasificarList where uds equals to UPDATED_UDS
        defaultClasificarShouldNotBeFound("uds.in=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds is not null
        defaultClasificarShouldBeFound("uds.specified=true");

        // Get all the clasificarList where uds is null
        defaultClasificarShouldNotBeFound("uds.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds is greater than or equal to DEFAULT_UDS
        defaultClasificarShouldBeFound("uds.greaterThanOrEqual=" + DEFAULT_UDS);

        // Get all the clasificarList where uds is greater than or equal to UPDATED_UDS
        defaultClasificarShouldNotBeFound("uds.greaterThanOrEqual=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds is less than or equal to DEFAULT_UDS
        defaultClasificarShouldBeFound("uds.lessThanOrEqual=" + DEFAULT_UDS);

        // Get all the clasificarList where uds is less than or equal to SMALLER_UDS
        defaultClasificarShouldNotBeFound("uds.lessThanOrEqual=" + SMALLER_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds is less than DEFAULT_UDS
        defaultClasificarShouldNotBeFound("uds.lessThan=" + DEFAULT_UDS);

        // Get all the clasificarList where uds is less than UPDATED_UDS
        defaultClasificarShouldBeFound("uds.lessThan=" + UPDATED_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByUdsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where uds is greater than DEFAULT_UDS
        defaultClasificarShouldNotBeFound("uds.greaterThan=" + DEFAULT_UDS);

        // Get all the clasificarList where uds is greater than SMALLER_UDS
        defaultClasificarShouldBeFound("uds.greaterThan=" + SMALLER_UDS);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso equals to DEFAULT_PESO
        defaultClasificarShouldBeFound("peso.equals=" + DEFAULT_PESO);

        // Get all the clasificarList where peso equals to UPDATED_PESO
        defaultClasificarShouldNotBeFound("peso.equals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso not equals to DEFAULT_PESO
        defaultClasificarShouldNotBeFound("peso.notEquals=" + DEFAULT_PESO);

        // Get all the clasificarList where peso not equals to UPDATED_PESO
        defaultClasificarShouldBeFound("peso.notEquals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsInShouldWork() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso in DEFAULT_PESO or UPDATED_PESO
        defaultClasificarShouldBeFound("peso.in=" + DEFAULT_PESO + "," + UPDATED_PESO);

        // Get all the clasificarList where peso equals to UPDATED_PESO
        defaultClasificarShouldNotBeFound("peso.in=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso is not null
        defaultClasificarShouldBeFound("peso.specified=true");

        // Get all the clasificarList where peso is null
        defaultClasificarShouldNotBeFound("peso.specified=false");
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso is greater than or equal to DEFAULT_PESO
        defaultClasificarShouldBeFound("peso.greaterThanOrEqual=" + DEFAULT_PESO);

        // Get all the clasificarList where peso is greater than or equal to UPDATED_PESO
        defaultClasificarShouldNotBeFound("peso.greaterThanOrEqual=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso is less than or equal to DEFAULT_PESO
        defaultClasificarShouldBeFound("peso.lessThanOrEqual=" + DEFAULT_PESO);

        // Get all the clasificarList where peso is less than or equal to SMALLER_PESO
        defaultClasificarShouldNotBeFound("peso.lessThanOrEqual=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsLessThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso is less than DEFAULT_PESO
        defaultClasificarShouldNotBeFound("peso.lessThan=" + DEFAULT_PESO);

        // Get all the clasificarList where peso is less than UPDATED_PESO
        defaultClasificarShouldBeFound("peso.lessThan=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPesoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        // Get all the clasificarList where peso is greater than DEFAULT_PESO
        defaultClasificarShouldNotBeFound("peso.greaterThan=" + DEFAULT_PESO);

        // Get all the clasificarList where peso is greater than SMALLER_PESO
        defaultClasificarShouldBeFound("peso.greaterThan=" + SMALLER_PESO);
    }

    @Test
    @Transactional
    void getAllClasificarsByPaisOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setPaisOrigen(paisOrigen);
        clasificarRepository.saveAndFlush(clasificar);
        Long paisOrigenId = paisOrigen.getId();

        // Get all the clasificarList where paisOrigen equals to paisOrigenId
        defaultClasificarShouldBeFound("paisOrigenId.equals=" + paisOrigenId);

        // Get all the clasificarList where paisOrigen equals to (paisOrigenId + 1)
        defaultClasificarShouldNotBeFound("paisOrigenId.equals=" + (paisOrigenId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByPaisDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setPaisDestino(paisDestino);
        clasificarRepository.saveAndFlush(clasificar);
        Long paisDestinoId = paisDestino.getId();

        // Get all the clasificarList where paisDestino equals to paisDestinoId
        defaultClasificarShouldBeFound("paisDestinoId.equals=" + paisDestinoId);

        // Get all the clasificarList where paisDestino equals to (paisDestinoId + 1)
        defaultClasificarShouldNotBeFound("paisDestinoId.equals=" + (paisDestinoId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByDivisaIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setDivisa(divisa);
        clasificarRepository.saveAndFlush(clasificar);
        Long divisaId = divisa.getId();

        // Get all the clasificarList where divisa equals to divisaId
        defaultClasificarShouldBeFound("divisaId.equals=" + divisaId);

        // Get all the clasificarList where divisa equals to (divisaId + 1)
        defaultClasificarShouldNotBeFound("divisaId.equals=" + (divisaId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByIdiomaIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setIdioma(idioma);
        clasificarRepository.saveAndFlush(clasificar);
        Long idiomaId = idioma.getId();

        // Get all the clasificarList where idioma equals to idiomaId
        defaultClasificarShouldBeFound("idiomaId.equals=" + idiomaId);

        // Get all the clasificarList where idioma equals to (idiomaId + 1)
        defaultClasificarShouldNotBeFound("idiomaId.equals=" + (idiomaId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByRefClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setRefCliente(refCliente);
        clasificarRepository.saveAndFlush(clasificar);
        Long refClienteId = refCliente.getId();

        // Get all the clasificarList where refCliente equals to refClienteId
        defaultClasificarShouldBeFound("refClienteId.equals=" + refClienteId);

        // Get all the clasificarList where refCliente equals to (refClienteId + 1)
        defaultClasificarShouldNotBeFound("refClienteId.equals=" + (refClienteId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByProvinciaDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setProvinciaDestino(provinciaDestino);
        clasificarRepository.saveAndFlush(clasificar);
        Long provinciaDestinoId = provinciaDestino.getId();

        // Get all the clasificarList where provinciaDestino equals to provinciaDestinoId
        defaultClasificarShouldBeFound("provinciaDestinoId.equals=" + provinciaDestinoId);

        // Get all the clasificarList where provinciaDestino equals to (provinciaDestinoId + 1)
        defaultClasificarShouldNotBeFound("provinciaDestinoId.equals=" + (provinciaDestinoId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByIdRemitenteIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setIdRemitente(idRemitente);
        clasificarRepository.saveAndFlush(clasificar);
        Long idRemitenteId = idRemitente.getId();

        // Get all the clasificarList where idRemitente equals to idRemitenteId
        defaultClasificarShouldBeFound("idRemitenteId.equals=" + idRemitenteId);

        // Get all the clasificarList where idRemitente equals to (idRemitenteId + 1)
        defaultClasificarShouldNotBeFound("idRemitenteId.equals=" + (idRemitenteId + 1));
    }

    @Test
    @Transactional
    void getAllClasificarsByIdDestinatarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);
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
        clasificar.setIdDestinatario(idDestinatario);
        clasificarRepository.saveAndFlush(clasificar);
        Long idDestinatarioId = idDestinatario.getId();

        // Get all the clasificarList where idDestinatario equals to idDestinatarioId
        defaultClasificarShouldBeFound("idDestinatarioId.equals=" + idDestinatarioId);

        // Get all the clasificarList where idDestinatario equals to (idDestinatarioId + 1)
        defaultClasificarShouldNotBeFound("idDestinatarioId.equals=" + (idDestinatarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClasificarShouldBeFound(String filter) throws Exception {
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificar.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].remitente").value(hasItem(DEFAULT_REMITENTE)))
            .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].codigoArancelarioOrigen").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].uds").value(hasItem(DEFAULT_UDS)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())));

        // Check, that the count call also returns 1
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClasificarShouldNotBeFound(String filter) throws Exception {
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClasificarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClasificar() throws Exception {
        // Get the clasificar
        restClasificarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClasificar() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();

        // Update the clasificar
        Clasificar updatedClasificar = clasificarRepository.findById(clasificar.getId()).get();
        // Disconnect from session so that the updates on updatedClasificar are not directly saved in db
        em.detach(updatedClasificar);
        updatedClasificar
            .descripcion(UPDATED_DESCRIPCION)
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .importe(UPDATED_IMPORTE)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(updatedClasificar);

        restClasificarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clasificarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
        Clasificar testClasificar = clasificarList.get(clasificarList.size() - 1);
        assertThat(testClasificar.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testClasificar.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testClasificar.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testClasificar.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
        assertThat(testClasificar.getProveedor()).isEqualTo(UPDATED_PROVEEDOR);
        assertThat(testClasificar.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testClasificar.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testClasificar.getUds()).isEqualTo(UPDATED_UDS);
        assertThat(testClasificar.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void putNonExistingClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clasificarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clasificarDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClasificarWithPatch() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();

        // Update the clasificar using partial update
        Clasificar partialUpdatedClasificar = new Clasificar();
        partialUpdatedClasificar.setId(clasificar.getId());

        partialUpdatedClasificar
            .descripcion(UPDATED_DESCRIPCION)
            .remitente(UPDATED_REMITENTE)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN);

        restClasificarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasificar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClasificar))
            )
            .andExpect(status().isOk());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
        Clasificar testClasificar = clasificarList.get(clasificarList.size() - 1);
        assertThat(testClasificar.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testClasificar.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testClasificar.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testClasificar.getDestinatario()).isEqualTo(DEFAULT_DESTINATARIO);
        assertThat(testClasificar.getProveedor()).isEqualTo(DEFAULT_PROVEEDOR);
        assertThat(testClasificar.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testClasificar.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testClasificar.getUds()).isEqualTo(DEFAULT_UDS);
        assertThat(testClasificar.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void fullUpdateClasificarWithPatch() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();

        // Update the clasificar using partial update
        Clasificar partialUpdatedClasificar = new Clasificar();
        partialUpdatedClasificar.setId(clasificar.getId());

        partialUpdatedClasificar
            .descripcion(UPDATED_DESCRIPCION)
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .importe(UPDATED_IMPORTE)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);

        restClasificarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClasificar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClasificar))
            )
            .andExpect(status().isOk());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
        Clasificar testClasificar = clasificarList.get(clasificarList.size() - 1);
        assertThat(testClasificar.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testClasificar.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testClasificar.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testClasificar.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
        assertThat(testClasificar.getProveedor()).isEqualTo(UPDATED_PROVEEDOR);
        assertThat(testClasificar.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testClasificar.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testClasificar.getUds()).isEqualTo(UPDATED_UDS);
        assertThat(testClasificar.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void patchNonExistingClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clasificarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClasificar() throws Exception {
        int databaseSizeBeforeUpdate = clasificarRepository.findAll().size();
        clasificar.setId(count.incrementAndGet());

        // Create the Clasificar
        ClasificarDTO clasificarDTO = clasificarMapper.toDto(clasificar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClasificarMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clasificarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clasificar in the database
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClasificar() throws Exception {
        // Initialize the database
        clasificarRepository.saveAndFlush(clasificar);

        int databaseSizeBeforeDelete = clasificarRepository.findAll().size();

        // Delete the clasificar
        restClasificarMockMvc
            .perform(delete(ENTITY_API_URL_ID, clasificar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clasificar> clasificarList = clasificarRepository.findAll();
        assertThat(clasificarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
