package ma.projet.graph.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Double montant;
    private String date; // Date in String format, such as "2024-11-21"
    private Long compteId; // ID of the associated account
    private TypeTransaction type;

}
