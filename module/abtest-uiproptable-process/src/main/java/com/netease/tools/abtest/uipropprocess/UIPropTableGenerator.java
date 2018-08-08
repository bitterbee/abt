package com.netease.tools.abtest.uipropprocess;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.abtest.uiprop.UIPropSetterAnno;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * Created by zyl06 on 2018/8/6.
 */

public class UIPropTableGenerator extends BaseClassGenerator {

    private List<UIPropCreatorModel> mCreators = new ArrayList<>();
    private List<UIPropSetterModel> mSetters = new ArrayList<>();

    private static final String UI_PROP_CREATORS = "uiPropCreators";
    private static final String UI_PROP_SETTERS = "uiPropSetters";

    public UIPropTableGenerator(Messager messager) {
        super(messager);
    }

    @Override
    public String className() {
        return "ABTestUIPropTable";
    }

    @Override
    public TypeSpec generate(String packageName) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(className())
                .addModifiers(Modifier.PUBLIC);

        FieldSpec uiPropCreators = FieldSpec
                .builder(ParameterizedTypeName.get(HashMap.class, Class.class, UIPropCreatorAnno.class), UI_PROP_CREATORS,
                        Modifier.PRIVATE, Modifier.STATIC)
                .initializer("new $T<>()", TypeName.get(LinkedHashMap.class))
                .build();
        builder.addField(uiPropCreators);

        FieldSpec uiPropSetters = FieldSpec
                .builder(ParameterizedTypeName.get(HashMap.class, Object.class, UIPropSetterAnno.class), UI_PROP_SETTERS,
                        Modifier.PRIVATE, Modifier.STATIC)
                .initializer("new $T<>()", TypeName.get(LinkedHashMap.class))
                .build();
        builder.addField(uiPropSetters);

        MethodSpec.Builder uiPropCreatorsMethod = MethodSpec.methodBuilder("getUIPropCreators")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.SYNCHRONIZED)
                .returns(ParameterizedTypeName.get(HashMap.class, Class.class, UIPropCreatorAnno.class));
        uiPropCreatorsMethod.beginControlFlow("if (" + UI_PROP_CREATORS + ".isEmpty())");
        for (UIPropCreatorModel creatorModel : mCreators) {
            ClassName creator = ClassName.bestGuess(creatorModel.typeElement.toString());
            uiPropCreatorsMethod.addStatement(UI_PROP_CREATORS + ".put($T.class, $T.class.getAnnotation(UIPropCreatorAnno.class))", creator, creator);
        }
        uiPropCreatorsMethod.endControlFlow();
        uiPropCreatorsMethod.addStatement("return " + UI_PROP_CREATORS);
        builder.addMethod(uiPropCreatorsMethod.build());

        MethodSpec.Builder uiPropSettersMethod = MethodSpec.methodBuilder("getUIPropSetters")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.SYNCHRONIZED)
                .returns(ParameterizedTypeName.get(HashMap.class, Object.class, UIPropSetterAnno.class));
        uiPropSettersMethod.beginControlFlow("if (" + UI_PROP_SETTERS + ".isEmpty())");
        for (UIPropSetterModel setterModel : mSetters) {
            ClassName setter = ClassName.bestGuess(setterModel.typeElement.toString());
            uiPropSettersMethod.addStatement(UI_PROP_SETTERS + ".put(new $T(), $T.class.getAnnotation(UIPropSetterAnno.class))", setter, setter);
        }
        uiPropSettersMethod.endControlFlow();
        uiPropSettersMethod.addStatement("return " + UI_PROP_SETTERS);
        builder.addMethod(uiPropSettersMethod.build());

        return builder.build();
    }

    @Override
    public void printError(Exception e) {
        messager.printMessage(ERROR, "Couldn't generate UIPropTable class. cause " + e.toString());
    }

    public void bindAnnos(List<UIPropCreatorModel> creatorAnnos,
                          List<UIPropSetterModel> setterAnnos) {
        if (creatorAnnos != null) {
            mCreators.addAll(creatorAnnos);
        }
        if (setterAnnos != null) {
            mSetters.addAll(setterAnnos);
        }
    }
}
