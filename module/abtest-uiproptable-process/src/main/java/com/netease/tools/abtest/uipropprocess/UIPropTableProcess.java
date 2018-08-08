package com.netease.tools.abtest.uipropprocess;

import com.google.auto.service.AutoService;
import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.abtest.uiprop.UIPropSetterAnno;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.squareup.javapoet.JavaFile.builder;
import static javax.lang.model.SourceVersion.latestSupported;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

/**
 * Created by zyl06 on 2018/8/6.
 */
@AutoService(Processor.class)
public class UIPropTableProcess extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private String packageName = "com.netease.libs.abtest";
    private boolean isProceeded = false;

    private static final List<BaseClassGenerator> CLASS_GENERATORS = new ArrayList<BaseClassGenerator>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {
            {
                add(UIPropCreatorAnno.class.getCanonicalName());
                add(UIPropSetterAnno.class.getCanonicalName());
            }
        };
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(NOTE, "abtest process");
        if (isProceeded) {
            return true;
        }
        isProceeded = true;

        for (TypeElement element : annotations) {
            messager.printMessage(NOTE, element.getQualifiedName());
        }

        CLASS_GENERATORS.clear();
        CLASS_GENERATORS.add(newUITableGenerator(annotations, roundEnv));

        for (BaseClassGenerator generator : CLASS_GENERATORS) {
            try {
                TypeSpec generatedClass = generator.generate(packageName);
                JavaFile javaFile = builder(packageName, generatedClass).build();
                javaFile.writeTo(filer);
            } catch (IOException e) {
                generator.printError(e);
            }
        }

        return true;
    }

    private BaseClassGenerator newUITableGenerator(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        UIPropTableGenerator generator = new UIPropTableGenerator(messager);

        List<UIPropCreatorModel> creators = new ArrayList<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(UIPropCreatorAnno.class)) {
            TypeElement annotatedClass = (TypeElement) annotatedElement;
            //检测是否是支持的注解类型，如果不是里面会报错
            if (!isValidElement(annotatedClass, UIPropCreatorAnno.class)) {
                continue;
            }
            //获取到信息，把注解类的信息加入到列表中
            UIPropCreatorAnno anno = annotatedElement.getAnnotation(UIPropCreatorAnno.class);
            creators.add(new UIPropCreatorModel(annotatedClass, anno));
        }

        List<UIPropSetterModel> setters = new ArrayList<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(UIPropSetterAnno.class)) {
            TypeElement annotatedClass = (TypeElement) annotatedElement;
            //检测是否是支持的注解类型，如果不是里面会报错
            if (!isValidElement(annotatedClass, UIPropSetterAnno.class)) {
                continue;
            }
            //获取到信息，把注解类的信息加入到列表中
            UIPropSetterAnno anno = annotatedElement.getAnnotation(UIPropSetterAnno.class);
            setters.add(new UIPropSetterModel(annotatedClass, anno));
        }

        generator.bindAnnos(creators, setters);
        return generator;
    }

    private boolean isValidElement(Element annotatedClass, Class annoClass) {

        if (!isPublic(annotatedClass)) {
            String message = String.format("Classes annotated with %s must be public.", "@" + annoClass.getSimpleName());
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }

        if (isAbstract(annotatedClass)) {
            String message = String.format("Classes annotated with %s must not be abstract.", "@" + annoClass.getSimpleName());
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }

        return true;
    }

    static boolean isPublic(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(PUBLIC);
    }

    static boolean isAbstract(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(ABSTRACT);
    }
}
