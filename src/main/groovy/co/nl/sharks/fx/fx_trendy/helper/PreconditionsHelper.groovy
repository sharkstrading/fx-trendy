package co.nl.sharks.fx.fx_trendy.helper

import groovy.transform.CompileStatic

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull

@CompileStatic
class PreconditionsHelper {
    static boolean checkNotNullMulti(List<Object> objects, List<String> names) {
        if(objects == null || names == null)
            throw new NullPointerException("All arguments must be non-null")

        checkArgument(objects.size() > 0, "Must provide 1 or more arguments")
        checkArgument(names.size() > 0, "Must provide 1 or more names")
        checkArgument(objects.size() == names.size(), "Objects and names must be of equal size")

        for(int i = 0; i < objects.size(); i++) {
            checkNotNull(objects[i], "Argument %s may not be null", names[i])
        }
    }
}
