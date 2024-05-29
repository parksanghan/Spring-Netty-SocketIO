package org.socketio.demo.annotation;// annotation module
// CustomGetterProcessor.java

import com.google.auto.service.AutoService;

import org.springframework.javapoet.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@SupportedAnnotationTypes("org.socketio.demo.annotation.CustomGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class CustomGetterProcessor extends AbstractProcessor
{
    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        TestTT tt= new TestTT();

        // 타겟 annotation class 정의
        Set<String> set = new HashSet<>();
        set.add(CustomGetter.class.getName());

        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        // 타겟 source version 정의 (java 8, 11 ...)
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CustomGetter.class);

        for (Element element : elements)
        {
            if (element.getKind() != ElementKind.CLASS)
            {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "CustomGetter must be annotated on class");
                continue;
            }

            TypeElement typeElement = (TypeElement) element;

            List<FieldSpec> fieldSpecList = new ArrayList<>();
            List<MethodSpec> methodSpecList = new ArrayList<>();

            for (Element field : typeElement.getEnclosedElements())
            {
                if (field.getKind() == ElementKind.FIELD)
                {
                    String fieldName = field.getSimpleName().toString();
                    TypeName fieldType = TypeName.get(field.asType());

                    FieldSpec fieldSpec = FieldSpec.builder(fieldType, fieldName)
                            .addModifiers(Modifier.PRIVATE)
                            .build();
                    fieldSpecList.add(fieldSpec);

                    String methodName = "get" + StringUtils.capitalize(fieldName);
                    MethodSpec methodSpec = MethodSpec.methodBuilder(methodName)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(fieldType)
                            .addStatement("return $N", fieldName)
                            .build();
                    methodSpecList.add(methodSpec);
                }
            }

            ClassName className = ClassName.get(typeElement);
            String getterClassName = className.simpleName() + "Getter";

            TypeSpec getterClass = TypeSpec.classBuilder(getterClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addFields(fieldSpecList)
                    .addMethods(methodSpecList)
                    .build();

            try
            {
                JavaFile.builder(className.packageName(), getterClass)
                        .build()
                        .writeTo(processingEnv.getFiler());
            }
            catch (IOException e)
            {
                TestTT tt= new TestTT();

                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "ERROR : " + e);
            }
        }

        return true;
    }
}