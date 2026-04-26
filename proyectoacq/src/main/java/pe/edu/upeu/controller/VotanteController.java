package pe.edu.upeu.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.upeu.model.Votante;
import pe.edu.upeu.service.VotanteServiceImp;
import pe.edu.upeu.service.VotanteServiceInter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VotanteController {

    @FXML
    private TextField txtFolio;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtRfc;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtDistrito;
    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<Votante> tvVotantes;
    @FXML
    private TableColumn<Votante, String> colFolio;
    @FXML
    private TableColumn<Votante, String> colNombre;
    @FXML
    private TableColumn<Votante, String> colRfc;
    @FXML
    private TableColumn<Votante, String> colSeccion;
    @FXML
    private TableColumn<Votante, String> colDistrito;

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;

    private final VotanteServiceInter service;
    private final ObservableList<Votante> votantesList;

    public VotanteController() {
        this.service = new VotanteServiceImp();
        this.votantesList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        colFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colRfc.setCellValueFactory(new PropertyValueFactory<>("rfc"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<>("seccionElectoral"));
        colDistrito.setCellValueFactory(new PropertyValueFactory<>("distrito"));

        tvVotantes.setItems(votantesList);

        // Detectar selección en la tabla
        tvVotantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDetalles(newSelection);
            }
        });

        actualizarTabla();
    }

    @FXML
    public void guardarAction() {
        if (txtNombre.getText().isEmpty() || txtRfc.getText().isEmpty() ||
            txtSeccion.getText().isEmpty() || txtDistrito.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de validación", "Todos los campos son obligatorios.");
            return;
        }

        String folio = txtFolio.getText();
        if (folio == null || folio.isEmpty()) {
            // Nuevo registro (Alta)
            folio = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Votante nuevoVotante = Votante.builder()
                    .folio(folio)
                    .nombreCompleto(txtNombre.getText())
                    .DNIrfc(txtRfc.getText())
                    .seccionElectoral(txtSeccion.getText())
                    .distrito(txtDistrito.getText())
                    .build();
            service.registrarVotante(nuevoVotante);
        } else {
            // Modificación
            Votante votanteEditado = Votante.builder()
                    .folio(folio)
                    .nombreCompleto(txtNombre.getText())
                    .DNIrfc(txtRfc.getText())
                    .seccionElectoral(txtSeccion.getText())
                    .distrito(txtDistrito.getText())
                    .build();
            service.actualizarVotante(votanteEditado);
        }

        limpiarAction();
        actualizarTabla();
    }

    @FXML
    public void eliminarAction() {
        Votante seleccionado = tvVotantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            service.eliminarVotante(seleccionado.getFolio());
            limpiarAction();
            actualizarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un votante de la tabla para eliminar.");
        }
    }

    @FXML
    public void limpiarAction() {
        txtFolio.clear();
        txtNombre.clear();
        txtRfc.clear();
        txtSeccion.clear();
        txtDistrito.clear();
        txtBuscar.clear();
        tvVotantes.getSelectionModel().clearSelection();
    }

    @FXML
    public void buscarAction() {
        String query = txtBuscar.getText();
        if (query == null || query.trim().isEmpty()) {
            actualizarTabla();
            return;
        }

        // Buscar por folio exacto primero
        Optional<Votante> porFolio = service.buscarPorFolio(query);
        if (porFolio.isPresent()) {
            votantesList.setAll(porFolio.get());
            return;
        }

        // Si no encuentra por folio, buscar por nombre
        List<Votante> porNombre = service.buscarPorNombre(query);
        votantesList.setAll(porNombre);
    }

    private void mostrarDetalles(Votante votante) {
        txtFolio.setText(votante.getFolio());
        txtNombre.setText(votante.getNombreCompleto());
        txtRfc.setText(votante.getDNIrfc());
        txtSeccion.setText(votante.getSeccionElectoral());
        txtDistrito.setText(votante.getDistrito());
    }

    @FXML
    public void actualizarTabla() {
        votantesList.setAll(service.obtenerTodos());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
