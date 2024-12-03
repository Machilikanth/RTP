package com.toucan.tourtptrs.models.response;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.toucan.tourtptrs.constants.EnumConstants.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VpaTransactionResponseHelper {

    @Id
    private String id;

    private String userId;

    private String creditVpaId;

    private String debitVpaId;

    private TransactionStatus status;

    private LocalDateTime createAt;

}
