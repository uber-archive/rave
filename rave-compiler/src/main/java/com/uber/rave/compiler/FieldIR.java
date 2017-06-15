package com.uber.rave.compiler;

import com.squareup.javapoet.TypeName;

/**
 * Represents the intermediate representation (IR) of a field within a particular data model class. This class holds
 * all the required information to generate a method which verifies the inner fields in a data model.
 */
final class FieldIR extends ElementIRBase {

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
    TypeName getTypeName() {
        return typeName;
    }
}
