package be.lukin.poeditor;

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
}
