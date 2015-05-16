package com.kanchutech.mitra.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kanchutech.mitra.model.Option;

@FacesConverter("optionConverter")
public class OptionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] arr = value.split(":::");
		return new Option(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), arr[2]);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
