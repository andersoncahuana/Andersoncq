package pe.edu.upeu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Votante {
    private String folio;
    private String nombreCompleto;
    private String DNIrfc;
    private String seccionElectoral;
    private String distrito;
}
