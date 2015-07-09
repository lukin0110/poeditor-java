package be.lukin.poeditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://poeditor.com/api_reference/#export
 */
public enum FilterByEnum {
    TRANSLATED,
    UNTRANSLATED,
    FUZZY,
    NOT_FUZZY,
    AUTOMATIC,
    NOT_AUTOMATIC,
    PROOFREAD,
    NOT_PROOFREAD;
    
    public static String[] toStringArray(FilterByEnum[] filters){
        if(filters != null){
            String[] array = new String[filters.length];
            
            for(int i=0; i<filters.length; i++){
                if(filters[i] != null) {
                    array[i] = filters[i].toString().toLowerCase();
                }
            }
            
            return array;
        }
        
        return null;
    }
    
    public static FilterByEnum[] toArray(String[] filters){
        if(filters != null){    
            ArrayList<FilterByEnum> list = new ArrayList<FilterByEnum>();

            for(int i=0; i<filters.length; i++){
                if(filters[i] != null){
                    try {
                        list.add(FilterByEnum.valueOf(filters[i].trim().toUpperCase()));
                    } catch(IllegalArgumentException iae) {
                        // silently pass
                    }
                }
            }

            return list.toArray(new FilterByEnum[list.size()]);
        }
        
        return null;
    }
}
