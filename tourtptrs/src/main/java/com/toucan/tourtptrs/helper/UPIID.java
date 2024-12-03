package com.toucan.tourtptrs.helper;

import com.toucan.tourtptrs.constants.EnumConstants.vpaStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UPIID {
private String vpa;
private vpaStatusEnum  statusEnum;
}
