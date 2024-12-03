package com.ppl2.smartshop.until;

import javax.persistence.AttributeConverter;

import com.ppl2.smartshop.entities.datatypes.constrains.UnitVoucher;
import java.util.stream.Stream;
public class UnitVoucherConverter implements AttributeConverter<UnitVoucher, String>{

	@Override
	public String convertToDatabaseColumn(UnitVoucher attribute) {
	       if (attribute == null) {
	            return null;
	        }
	        return attribute.getCode();
	}

	@Override
	public UnitVoucher convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(UnitVoucher.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
	}

}
