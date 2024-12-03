package com.toucan.rtp.core.helper;

import com.toucan.rtp.core.constants.AppConstants.vpaStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UPIID {
private String vpa;
private vpaStatusEnum statusEnum;
}
