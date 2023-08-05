package com.example.moneymanagerbe.domain.dto.response;

import com.example.moneymanagerbe.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletResponseDto extends DateAuditingDto {

    private String id;

    private String name;

    private float total;
}
