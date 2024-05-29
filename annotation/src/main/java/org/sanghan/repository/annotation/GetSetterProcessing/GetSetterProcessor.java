package org.sanghan.repository.annotation.GetSetterProcessing;

import com.google.auto.service.AutoService;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.Set;
import com.squareup.javapoet.*;




@SupportedAnnotationTypes(value = "org.sanghan.repository.annotation.GetSetterProcessing.GetSetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GetSetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GetSetter.class)) {
            if (element instanceof TypeElement) {
                generateGetSetter((TypeElement) element);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@GetSetter have to apply classes", element);
            }
        }
        return true;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void generateGetSetter(TypeElement element) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement field = (VariableElement) enclosedElement;
                generateGetterAndSetter(element, field);
            }
        }
    }

    private void generateGetterAndSetter(TypeElement element, VariableElement field) {
        String fieldName = field.getSimpleName().toString();
        String capitalizedFieldName = capitalize(fieldName);

        MethodSpec getter = MethodSpec.methodBuilder("get" + capitalizedFieldName)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(field.asType()))
                .addStatement("return $N", fieldName)
                .build();


        MethodSpec setter = MethodSpec.methodBuilder("set" + capitalizedFieldName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(TypeName.get(field.asType()), fieldName)
                .addStatement("this.$N = $N", fieldName, fieldName)
                .build();

        TypeSpec generatedClass = TypeSpec.classBuilder(element.getSimpleName().toString())
                .addMethod(getter)
                .addMethod(setter)

                .addField(TypeName.get(field.asType()), fieldName, Modifier.PRIVATE) // 필드 추가
                .build();

        //

        //

        JavaFile javaFile = JavaFile.builder("org.sanghan.generated", generatedClass)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 }
