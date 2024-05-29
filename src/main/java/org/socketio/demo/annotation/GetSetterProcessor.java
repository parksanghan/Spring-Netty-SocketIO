package org.socketio.demo.annotation;

import com.google.auto.service.AutoService;

import org.springframework.javapoet.JavaFile;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.TypeName;
import org.springframework.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("GetSetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GetSetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // @GetSetter 애노테이션이 적용된 요소를 찾아서 처리합니다.

        for (Element element : roundEnv.getElementsAnnotatedWith(GetSetter.class)) {
            if (element instanceof TypeElement) {
                // 클래스인 경우, getter 및 setter 메서드를 생성합니다.
                generateGetterSetter((TypeElement) element);
            } else {
                // 클래스가 아닌 경우, 오류 메시지를 출력합니다.
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "@GetSetter can only be applied to classes", element);
            }
        }
        return true;
    }

    // getter 및 setter 메서드를 생성하는 메서드입니다.
    private void generateGetterSetter(TypeElement element) {
        // 클래스에 정의된 필드를 순회하면서 getter 및 setter 메서드를 생성합니다.
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) enclosedElement;
                // 각 필드에 대해 getter 메서드를 생성합니다.
                generateGetter(element, field);
                // 각 필드에 대해 setter 메서드를 생성합니다.
                generateSetter(element, field);
            }
        }
    }

    // getter 메서드를 생성하는 메서드입니다.
    private void generateGetter(TypeElement element, VariableElement field) {
        MethodSpec getterMethod = MethodSpec.methodBuilder("get" + capitalize(field.getSimpleName().toString()))
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(field.asType()))
                .addStatement("return this.$N", field.getSimpleName())
                .build();

        TypeSpec generatedClass = TypeSpec.classBuilder
                        (element.getSimpleName() + "Generated")
                .addMethod(getterMethod)
                .build();

        JavaFile javaFile = JavaFile.builder
                        (element.getEnclosingElement().toString(),
                                generatedClass)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // setter 메서드를 생성하는 메서드입니다.
    private void generateSetter(TypeElement element, VariableElement field) {
        MethodSpec setterMethod = MethodSpec.methodBuilder("set" + capitalize(field.getSimpleName().toString()))
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(TypeName.get(field.asType()), field.getSimpleName().toString())
                .addStatement("this.$N = $N", field.getSimpleName(), field.getSimpleName())
                .build();

        TypeSpec generatedClass = TypeSpec.classBuilder(element.getSimpleName() + "Generated")
                .addMethod(setterMethod)
                .build();

        JavaFile javaFile = JavaFile.builder(element.getEnclosingElement().toString(), generatedClass)
                .build();
        System.out.println("Generated file: " + javaFile);


        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 첫 글자를 대문자로 변환하는 메서드입니다.
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
