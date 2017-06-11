package com.uber.rave.compiler;

import com.squareup.javapoet.TypeName;

/**
 * Todo Behrooz.
 */
public class FieldIR extends ElementIRBase {

    private final TypeName typeName;

    /**
     * Create a new field IR object.
     * @param fieldName the name of the field that this IR represents.
     * @param typeName
     */
    FieldIR(String fieldName, TypeName typeName) {
        super(fieldName);
        this.typeName = typeName;
    }

    /**
     * @return get the {@link TypeName} for this field.
     */
    public TypeName getTypeName() {
        return typeName;
    }
}
