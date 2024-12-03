package com.toucan.rtp.helper;


import com.toucan.rtp.constants.AppConstants.vpaStatusEnum;

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
