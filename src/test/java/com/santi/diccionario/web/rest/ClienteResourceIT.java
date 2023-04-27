package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Cliente;
import com.santi.diccionario.domain.Pais;
import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.repository.ClienteRepository;
import com.santi.diccionario.service.ClienteService;
import com.santi.diccionario.service.criteria.ClienteCriteria;
import com.santi.diccionario.service.dto.ClienteDTO;
import com.santi.diccionario.service.mapper.ClienteMapper;
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
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClienteResourceIT {

    private static final String DEFAULT_ID_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_ID_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @Autowired
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteService clienteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .idCliente(DEFAULT_ID_CLIENTE)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .direccion(DEFAULT_DIRECCION);
        // Add required entity
        TipoCliente tipoCliente;
        if (TestUtil.findAll(em, TipoCliente.class).isEmpty()) {
            tipoCliente = TipoClienteResourceIT.createEntity(em);
            em.persist(tipoCliente);
            em.flush();
        } else {
            tipoCliente = TestUtil.findAll(em, TipoCliente.class).get(0);
        }
        cliente.setTipoCliente(tipoCliente);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        cliente.setPais(pais);
        return cliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .idCliente(UPDATED_ID_CLIENTE)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);
        // Add required entity
        TipoCliente tipoCliente;
        if (TestUtil.findAll(em, TipoCliente.class).isEmpty()) {
            tipoCliente = TipoClienteResourceIT.createUpdatedEntity(em);
            em.persist(tipoCliente);
            em.flush();
        } else {
            tipoCliente = TestUtil.findAll(em, TipoCliente.class).get(0);
        }
        cliente.setTipoCliente(tipoCliente);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createUpdatedEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        cliente.setPais(pais);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getIdCliente()).isEqualTo(DEFAULT_ID_CLIENTE);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCliente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void createClienteWithExistingId() throws Exception {
        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCliente").value(hasItem(DEFAULT_ID_CLIENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(clienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClienteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.idCliente").value(DEFAULT_ID_CLIENTE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getClientesByIdFiltering() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        Long id = cliente.getId();

        defaultClienteShouldBeFound("id.equals=" + id);
        defaultClienteShouldNotBeFound("id.notEquals=" + id);

        defaultClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente equals to DEFAULT_ID_CLIENTE
        defaultClienteShouldBeFound("idCliente.equals=" + DEFAULT_ID_CLIENTE);

        // Get all the clienteList where idCliente equals to UPDATED_ID_CLIENTE
        defaultClienteShouldNotBeFound("idCliente.equals=" + UPDATED_ID_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente not equals to DEFAULT_ID_CLIENTE
        defaultClienteShouldNotBeFound("idCliente.notEquals=" + DEFAULT_ID_CLIENTE);

        // Get all the clienteList where idCliente not equals to UPDATED_ID_CLIENTE
        defaultClienteShouldBeFound("idCliente.notEquals=" + UPDATED_ID_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente in DEFAULT_ID_CLIENTE or UPDATED_ID_CLIENTE
        defaultClienteShouldBeFound("idCliente.in=" + DEFAULT_ID_CLIENTE + "," + UPDATED_ID_CLIENTE);

        // Get all the clienteList where idCliente equals to UPDATED_ID_CLIENTE
        defaultClienteShouldNotBeFound("idCliente.in=" + UPDATED_ID_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente is not null
        defaultClienteShouldBeFound("idCliente.specified=true");

        // Get all the clienteList where idCliente is null
        defaultClienteShouldNotBeFound("idCliente.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente contains DEFAULT_ID_CLIENTE
        defaultClienteShouldBeFound("idCliente.contains=" + DEFAULT_ID_CLIENTE);

        // Get all the clienteList where idCliente contains UPDATED_ID_CLIENTE
        defaultClienteShouldNotBeFound("idCliente.contains=" + UPDATED_ID_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClientesByIdClienteNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where idCliente does not contain DEFAULT_ID_CLIENTE
        defaultClienteShouldNotBeFound("idCliente.doesNotContain=" + DEFAULT_ID_CLIENTE);

        // Get all the clienteList where idCliente does not contain UPDATED_ID_CLIENTE
        defaultClienteShouldBeFound("idCliente.doesNotContain=" + UPDATED_ID_CLIENTE);
    }

    @Test
    @Transactional
    void getAllClientesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre equals to DEFAULT_NOMBRE
        defaultClienteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the clienteList where nombre equals to UPDATED_NOMBRE
        defaultClienteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllClientesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre not equals to DEFAULT_NOMBRE
        defaultClienteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the clienteList where nombre not equals to UPDATED_NOMBRE
        defaultClienteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllClientesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultClienteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the clienteList where nombre equals to UPDATED_NOMBRE
        defaultClienteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllClientesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre is not null
        defaultClienteShouldBeFound("nombre.specified=true");

        // Get all the clienteList where nombre is null
        defaultClienteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByNombreContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre contains DEFAULT_NOMBRE
        defaultClienteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the clienteList where nombre contains UPDATED_NOMBRE
        defaultClienteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllClientesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre does not contain DEFAULT_NOMBRE
        defaultClienteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the clienteList where nombre does not contain UPDATED_NOMBRE
        defaultClienteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion equals to DEFAULT_DESCRIPCION
        defaultClienteShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the clienteList where descripcion equals to UPDATED_DESCRIPCION
        defaultClienteShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultClienteShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the clienteList where descripcion not equals to UPDATED_DESCRIPCION
        defaultClienteShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultClienteShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the clienteList where descripcion equals to UPDATED_DESCRIPCION
        defaultClienteShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion is not null
        defaultClienteShouldBeFound("descripcion.specified=true");

        // Get all the clienteList where descripcion is null
        defaultClienteShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion contains DEFAULT_DESCRIPCION
        defaultClienteShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the clienteList where descripcion contains UPDATED_DESCRIPCION
        defaultClienteShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClientesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultClienteShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the clienteList where descripcion does not contain UPDATED_DESCRIPCION
        defaultClienteShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllClientesByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion equals to DEFAULT_DIRECCION
        defaultClienteShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the clienteList where direccion equals to UPDATED_DIRECCION
        defaultClienteShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllClientesByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion not equals to DEFAULT_DIRECCION
        defaultClienteShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the clienteList where direccion not equals to UPDATED_DIRECCION
        defaultClienteShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllClientesByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultClienteShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the clienteList where direccion equals to UPDATED_DIRECCION
        defaultClienteShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllClientesByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion is not null
        defaultClienteShouldBeFound("direccion.specified=true");

        // Get all the clienteList where direccion is null
        defaultClienteShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllClientesByDireccionContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion contains DEFAULT_DIRECCION
        defaultClienteShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the clienteList where direccion contains UPDATED_DIRECCION
        defaultClienteShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllClientesByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where direccion does not contain DEFAULT_DIRECCION
        defaultClienteShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the clienteList where direccion does not contain UPDATED_DIRECCION
        defaultClienteShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllClientesByTipoClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        TipoCliente tipoCliente;
        if (TestUtil.findAll(em, TipoCliente.class).isEmpty()) {
            tipoCliente = TipoClienteResourceIT.createEntity(em);
            em.persist(tipoCliente);
            em.flush();
        } else {
            tipoCliente = TestUtil.findAll(em, TipoCliente.class).get(0);
        }
        em.persist(tipoCliente);
        em.flush();
        cliente.setTipoCliente(tipoCliente);
        clienteRepository.saveAndFlush(cliente);
        Long tipoClienteId = tipoCliente.getId();

        // Get all the clienteList where tipoCliente equals to tipoClienteId
        defaultClienteShouldBeFound("tipoClienteId.equals=" + tipoClienteId);

        // Get all the clienteList where tipoCliente equals to (tipoClienteId + 1)
        defaultClienteShouldNotBeFound("tipoClienteId.equals=" + (tipoClienteId + 1));
    }

    @Test
    @Transactional
    void getAllClientesByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        em.persist(pais);
        em.flush();
        cliente.setPais(pais);
        clienteRepository.saveAndFlush(cliente);
        Long paisId = pais.getId();

        // Get all the clienteList where pais equals to paisId
        defaultClienteShouldBeFound("paisId.equals=" + paisId);

        // Get all the clienteList where pais equals to (paisId + 1)
        defaultClienteShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCliente").value(hasItem(DEFAULT_ID_CLIENTE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));

        // Check, that the count call also returns 1
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente.idCliente(UPDATED_ID_CLIENTE).nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).direccion(UPDATED_DIRECCION);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getIdCliente()).isEqualTo(UPDATED_ID_CLIENTE);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCliente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void putNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente.descripcion(UPDATED_DESCRIPCION);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getIdCliente()).isEqualTo(DEFAULT_ID_CLIENTE);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCliente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    void fullUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente
            .idCliente(UPDATED_ID_CLIENTE)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getIdCliente()).isEqualTo(UPDATED_ID_CLIENTE);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCliente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void patchNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
