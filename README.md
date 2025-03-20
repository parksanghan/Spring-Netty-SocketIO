# Spring Boot & Java Spring

# 자바에서 빌드 관리 도구란 ?

프로젝트에 필요한 XML , Properties , jar 파일들을 자동으로 인식하여 빌드 해주는 도구 소스 코드 컴파일 , 테스트 , 정적 분석 실행가능한 앱으로 빌드해줌 

프로젝트 정보 관리 , 테스트 빌드 , 배포 등의 작업을 진행해줌

외부 라이브러리를 참조하여 자동으로 다운로드 및 업데이트의 관리해줌 

EX ) ANT , MAVEN , Gradle 

> 메이븐이란 ?
> 

자바의 대표적인 관리 도구였던 Ant 를 대체하기 위해 개발됨

프로젝트 외부 라이브러리를 쉽게 참조할 수 있게  pom.xml 파일로 명시하여 관리 

참조한 외부 라이브러리에 연관된 다른 라이브러리도 자동으로 관리됨. 

즉 , 어떠한 특정  외부 라이브러리를 참조할때 그 참조하고 있는 라이브러리가 의존하고 있는 라이브러리를  컴파일에서 관리해줌 .

⇒ 즉 , 메이븐이 의존하고 있는 라이브러리들을 프로젝트에 자동으로 추가해주고 관리해준다

# 자바의 메모리 구조

JVM이란 JVM (Java Virtual Machine)로써 자바 어플리케이션을  어떤 CPU나 OS에서도 실행할 수 있다.

JVM의 4가지 구성 

- `Class Loader` :   클래스 파일을 동적으로 로드하고 링크하는 역할 클래스 로더는 JVM이 클래스를 처음 참조할 때 해당 클래스 파일을 로드하고 메모리에 로드된 클래스를 로드할 수 있도록 함.
- `Excution Engine`  :  실행 엔진으로써  Java 바이트 코드를 실행하는 역할을 담당. 실행 엔진은 JVM 내부에서 클래스 로더에 의해 로드된 클래슬 파일의 바이트 코드로 읽어 들이고 ,  해당 바이트 코드를 실제로 실행합니다.  ⇒ 즉 , 컴파일러가 변환한 JAVA 소스 코드의 바이트 코드는 JVM이 실행하는데 사용되고 실행 엔진은 이 바이트 코드를 읽어들이고 , 해당하는 명령을 실행하여 프로그램을 실행.(여기에 GC 있음 )
- `Garbage Collector` :  가비지 콜렉터는 더 이상 사용되지않는 JAVA 객체의 메모리를 회수 하는 역할.
- `Runtime Data Area` :  런타임 데이터 영역은 프로그램을실행하는 동안에 데이터를 저장하고 관리하는 역할

> 자바 어플리케이션의 실행과정
> 
1. 어플리케이션이 실행되면 JVM이 OS 로부터 메모리를 할당 받음
- JVM은 할당 받은 메모리를 용도에 따라 영역을 구분하여 관리
2. 자바 컴파일러 javac.exe 가 자바 소스코드를 읽어 바이트 코드로 변환
3. Class Loader를 통해 바이트 코드를 JVM으로 로딩
4. 로딩 된 바이트 코드는 Excution Engine을 통해 해석  됨.
5. 해석 된 바이트 코드는 Runtime Data Area에 배치되어 실행됨 
- 실행되는 과정에서 GC 같은 작업이 수행됨.  

> Excution Engine
> 

RunTime Data Area 에 할당된 바이트 코드를 실행시키는 주체 

코드를 실행하는 방식은 크게 2가지 방식이 존재 

`Interpreter` 

- 바이트 코드를 해석하여 실행하는 역할을 수행
- 다만 같은 메서드라도 여러번 호출될때 매번 새로 수행해야함

→  즉 , 같은 함수 메서드여도 여러 번 호출될때 바이트 코드를 새로 해석

`JIT (Just In Time) Compiler` 

- Interpreter 의 단점을 해소
- 반복되는 코드를 발견하여 전체 바이트 코드를 컴파일하고 그것을 Native Code 로 변경하여 사용
    
    → 즉 , 컴파일러는 프로그램이 실행되는 동안 반복되는 코드를 식별하고 이를 바이트 코드에서 네이티브 코드로 변환하여 속도를 향상 
    
    ⇒ 바이트 코드(.Class) 형식에서 Native Code(기계어)로 변환하여 사용  (인터프터와 같이 생성 코드를 캐싱하지 않고 같은 메서드여도 매번 새로 생성 )
    
    기계어(컴파일된 코드)는 캐시에 보관하기 때문에 한 번 컴파일된 코드는 빠르게 수행하게 된다.
    

`Garbage Collector` 

- 더 이상 참조되지 않는 메모리객체를모아 제거하는 역할을 수행
- 일반적으로 자동으로 실행되지만 , 수동으로 실행하기 위해 System.gc()를 사용할 수 있다.

> Classs Loader
> 

.java 파일은 소스코드로써 java Compiler 가 컴파일을 통해 소스코드를 바이트 코드로 해석 후 .class 형식으로 로드  되고  이 바이트 코드들이  Class Loader를 통해 로딩과 링킹 ,  이니셜 해당 단계들을 거쳐 Runtime Data Area에 저장이 된다.

→JVM으로 바이트 코드(.class)를 로드하고 , 링크를 통해 배치하는 작업을 수행하는 모듈 로드 된 바이트 코드들을 엮어서 JVM 메모리 영역인 Runtime Data Areas 에 배치함. 

이때, 클래스를 메모리에 올리는 로딩 기능은 한번에 메모리에 올리지 않고 , 어플리케이션에서 필요한 경우 동적으로 메모리에 적재하게 된다.

즉 → 필요한 경우는 호출되는 경우고 한번에 로딩기능을 올리지 않고 호출되는 경우 메모리에 올린다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled.png)

> Runtime Data Area
> 

어플리케이션이 동작하기 위해 OS에서 할당받은 메모리 공간을 의미

`5가지 구성` 

- `Method Area` :  Static 으로 선언된 변수들을 포함하여 Class 레벨의 모든 데이터가 이곳에 저장. JVM마다 단 하나의 Method Area가 존재 
`Method Area`에는 `RunTime Constant Pool` 이라는 별도의 영역이 존재 - 상수 자료형을 저장하여 참조하는역할

"JVM 마다 단 하나의 Method Area가 존재한다"는 것은 각 JVM 인스턴스가 실행될 때 메모리 내에 하나의 Method Area가 생성되며, 이 Method Area는 JVM 인스턴스당 단 하나만 존재한다는 의미입니다.

여러 개의 자바 프로그램이나 애플리케이션이 실행될 때, 각각의 `JVM` 인스턴스가 생성되고 각 JVM은 자체적으로 하나의 `Method Area`를 가지게 됩니다.
- 저장되는 정보의 종류 
→ `Field Info`  : 멤버 변수의 이름, 데이터 타입 , 접근 제어자 정보
→ `Method Info` :   메소드 이름 , Return 타입 , 매개변수  , 접근 제어자 정보 
→ `TYPE Info` : Class 인지 Interface인지 여부 저장. Type의 속성  , 이름 , Super Class의 이름 
 `Heap 과 마찬가지로 Gc 관리 대상`   

- Heap Area(ver.java 8 )

객체를 저장하기 위한 메모리 영역  new 연산자로 생성된 모든 Object 와 Instance 변수 , 그리고 배열을 저장 
Heap 영역은 물리적으로 두 영역으로 구분 할 수 있다. (Young Generation , Old Generation) 

→ Young Generation : 생명 주기가 짧은 객체를 GC 대상으로 하는 영역
Eden에 할당 후 Survivor 0과 1을 거쳐 오래 사용되는 객체를 Old Generation 으로 이동시킴

Young Generation은 다시 Eden 영역과 두 개의 Survivor 영역(Survivor Space)으로 나누어집니다. 새로운 객체는 Eden 영역에 할당됩니다. 이후 Young Generation에서는 주기적으로 GC(Garbage Collection)가 발생하여 Eden 영역에 있는 객체 중에서 참조되지 않는 객체들을 제거하고, 살아남은 객체는 Survivor 영역 중 하나로 이동됩니다. 이 과정을 반복하면서, 살아남은 객체들은 점점 더 오래된 Survivor 영역으로 이동하게 됩니다. 일정 시간이 지나거나 Survivor 영역이 가득 차면, 객체들은 Old Generation으로 이동하게 됩니다.
→ Old Generation :   생명 주기가 긴 객체를 GC 대상으로 하는 영역 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%201.png)

객체가 생성될떄 Eden으로 들어가며 Gc에서 객체가 참조가 되면 s0 할당 이후에 GC가 발생하면 Eden애서 살아남은 객체들은 S0으로 가고  S0에 있는 객체들이 GC에서 참조가 된다면 S1으로 이동하는 구조 

→ 이후 게속해서 참조되는 객체들을 Old Generation 에 할당 이후에도 참조 되지 않는다면 Old 

Minor GC:

Young Generation에서 발생하는 GC를 Minor GC라고 합니다.
대부분의 객체는 Young Generation(Eden 영역 및 Survivor 영역)에 할당되며, 새로운 객체가 생성될 때마다 이 영역에 할당됩니다.
Minor GC는 Young Generation에서만 발생하며, 주로 새로 생성된 객체들의 메모리를 회수합니다.
Minor GC가 발생하면, Eden 영역과 Survivor 영역에서 참조되지 않는 객체들이 제거되고, 살아남은 객체들은 다른 Survivor 영역으로 이동합니다.

Major GC (또는 Full GC):

Old Generation에서 발생하는 GC를 Major GC 또는 Full GC라고 합니다.
Old Generation에는 Young Generation에서 살아남은 오래된 객체들이 할당됩니다.
Major GC는 Old Generation에서 발생하며, 주로 Old Generation에 있는 객체들의 메모리를 회수합니다.
Major GC가 발생하면, Old Generation에서 참조되지 않는 객체들이 제거되고, 메모리 공간이 회수됩니다.
Major GC는 일반적으로 Young Generation의 Minor GC보다 더 많은 시간과 비용이 소요되며, 애플리케이션의 일시적인 중지를 유발할 수 있습니다.

Method Area 와 Heap Area 는 여러 쓰레드에서 공유되는 메모리 

- Stack Area

각 쓰레드를 위한 분리된 Runtime stack 영역

메소드를 호출할 때마다 Stack Frame으로 불리는 Entry가 Stack Area에 생성 됨.
쓰레드의 역할이 종료되면 바로 소멸되는 특성의 데이터 저장

각종 형태의 변수나 임시 데이터 , 쓰레드 또는 메소드의 정보를 저장

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%202.png)

 메서드가 호출될 때마다 해당 메서드에 대한 정보를 저장하는 공간인 Stack Frame(스택 프레임)이 생성됩니다. 이 스택 프레임은 메서드 호출 시점의 로컬 변수, 매개변수, 중첩 메서드 등과 같은 정보를 저장합니다.

- Pc Register

Program Counter 로써  각 스레드가 시작될 때 생성되며 현재 실행중인 상태 정보를 저장하는 영역 

Thread가 로직을 처리하면서 지속적으로 갱신됨 
Thread가 생성될때마다 하나씩 존재함

어떤 명령을 실행해야 할지에 대한 기록 (현재 수행중인 부분의 주소를 가짐)

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%203.png)

- Native Method Stack

바이트 코드가 아닌 실제 실행할수 있는 기계어로 작성된 프로그램을 실행 시키는 영역 떠한 JAVA 가 아닌 다른 언어로 작성된 코드를 위한 영역

JAVA Native Interface를 통해 바이트 코드로 치환하여 저장 

각 쓰레드 별로 생성됨

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%204.png)

# 자바의 Optional 클래스

> Optional 클래스 왜쓰는데
> 

`Null 포인터 Excetion 때문에 사용합니다.` 

nullPointerExcetion을 사용하여 참조 확인하는 로직을 사용할 수 있지만 코드가 난잡해진다.

```java
  public void checkExcetion(){
        try{
            System.out.println();

        }
        catch (NullPointerException error) {
            System.out.println(error.getMessage());
        }
    }
```

`Option Class`

```java
/    Optional<String >sample = Optional.empty(); //null으로 초기화
    Optional<People> optPeople = Optional.of( new People()); // null 이 아닌경우

    Optional<People >sample2 = Optional.ofNullable(optPeople.get());
    // 내부 값이  null 일 수 있는 경우 
```

> Optional 활용하기
> 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/80a4b8ec-ce70-4f0b-9f30-bd0c863b0815.png)

> Option :: filter 메서드
> 

`filter`  메서드는 predicate를 사용하여 해당 값이 참이면 필터를 통과시키는 메서드

```java
 public void func2(){
        ArrayList<Person> peopleListToCompare = new ArrayList<>(); // 비교할 리스트
        Optional<ArrayList<Person>> sample2 = Optional.ofNullable(getPeopleArray())
                .filter(array -> array.equals(peopleListToCompare));
    }
    public void func23() {

        Person Person = null;
        Optional<ArrayList<Person>> sample2 = Optional.ofNullable(getPeopleArray())
                .filter(array -> array.contains(Person));
    }
```

> Optional :: Map 메서드
> 

`Map`은 `Function`을 사용하여 입력값을 다른 값으로 변환하는 기능을 제공

```jsx
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Optional에 포함될 Person 객체 생성
        Person person = new Person("John");

        // Optional로 변환
        Optional<Person> optionalPerson = Optional.of(person);

        // Map 메서드를 사용하여 Optional에 포함된 Person 객체의 이름을 대문자로 변환
        Optional<String> upperCaseName = optionalPerson.map(p -> p.getName().toUpperCase());

        // 변환된 결과 출력
        upperCaseName.ifPresent(System.out::println); // JOHN
    }

    // Person 클래스 정의
    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

```

> Optional :: flatMap 사용법
> 

```jsx
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Optional에 포함될 Person 객체 생성
        Person person = new Person("John");

        // Optional로 변환
        Optional<Person> optionalPerson = Optional.of(person);

        // flatMap 메서드를 사용하여 Optional에 포함된 Person 객체의 이름을 Optional<String>으로 변환
        Optional<String> upperCaseName = optionalPerson.flatMap(p -> Optional.of(p.getName().toUpperCase()));

        // 변환된 결과 출력
        upperCaseName.ifPresent(System.out::println); // JOHN
    }

    // Person 클래스 정의
    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

```

flatMap 메서드는 매핑된 요소들을 단일한 단계로 평평하게 만듭니다. 일반적으로 매핑 함수가 요소를 다른 컬렉션으로 변환하는 경우 사용됩니다. 예를 들어, 스트림의 각 요소를 다른 스트림으로 매핑하고자 할 때 flatMap을 사용할 수 있습니다.

```java
public void myMap1(){
        Person Per = new Person("!1",1);

        Optional<String> optPerson = Optional.of(Per.getObj()).map(obj->obj.getName());
        // getobj 로 반환된 값을 람다식으로 호출
        Optional<String> optPerson1 = Optional.of(Per.getObj()).map(Person::getName);
        // 둘이 같은코드 : getobj로 반환된 값을 Person 클래스 내부의 getName 호출

    }
```

```jsx
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> numbers = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );

        // 각 리스트를 평평하게 만들기 위해 flatMap 사용
        List<Integer> flatList = numbers.stream()
                .flatMap(List::stream) // 각 리스트의 스트림을 평탄화
                .collect(Collectors.toList());

        System.out.println(flatList); // [1, 2, 3, 4, 5, 6]
    }
}

```

```java
    public void myFlatMap(){
        Optional<Optional<Person>> opt = Optional.of(Optional.of(new Person("jj",1)));
        Optional<String> optString =opt.flatMap(innerOpt -> innerOpt.map(Person::getName));

        Optional<Optional<Optional<Person>>> oppptt= Optional.of(Optional.of(Optional.of(new Person("!1",22))));
        Optional<Person> optPerson =  oppptt.flatMap(innerOpt->innerOpt.map(Optional::get));
    }
```

> Optional :: isPresent / is Empty 사용법
> 

isPresent는 최종적으로 연산이 끝나고 객체가 존재하는지 확인하는 기능을 제공  
isEmpty는 isPresent와 반대로 객체가 존재하지 않으면 true를 리턴 

```java
 public  void myIsPresent(){
        if (Optional.ofNullable(getPeopleArray()).isPresent()){
            System.out.println("is Present");
        }  // 값이 들어 있다면 true , 값이 없다면 false

        if (Optional.of(getPeopleArray()).isEmpty()) {
            System.out.println("is Empty");
        }// 값이 비어있다면 true 값이 있다면 false

        // 유지 보수하기 힘들게 하는법
        if(!Optional.ofNullable(getPeopleArray()).isPresent() &&Optional.ofNullable(getPeopleArray()).isEmpty()){
            System.out.println("is satisfy both is Present and is Empty");
        }
    }
    
```

> Optional :: get 사용법
> 

`get` : 최종적으로 나온 객체를 Optional 에서 꺼내는 기능을 제공 

객체가 존재하지 않다면 예외가 발생 

```java
 public Person getPsFrOpt(){
        Optional<Person> prOpt = Optional.of(new Person("!1",2));
        return prOpt.get();
         // Optional<Person> 즉 Optional 로 감싸져있는 Person에서 Person을 가져옴
    }
```

```java
public void testGetPsFrOpt(){
        Optional<Optional<Person>> prOpt = Optional.of(Optional.of(new Person("!1",2)));
        Person per=prOpt.get().get();
        String name = prOpt.get().get().getName();
        Optional<String>  pt =
         Optional.ofNullable(prOpt.get()).flatMap(Pr ->Pr.map(op->op.getName()));
        String  ptt= Optional.ofNullable(prOpt.get()).flatMap(Pr ->Pr.map(op->op.getName())).get();
        Optional<String> optStr = Optional.ofNullable(prOpt.get()).flatMap(Optvalue -> Optvalue.map(person-> person.getName()));

        Optional<Optional<String>> nestedOptional = Optional.of(Optional.of("value"));
        Optional<String> flattenedOptional = nestedOptional.flatMap(innerOptional -> innerOptional);
        // flatmap은 중첩된 구조를 풀어서 리턴함
        Optional<Optional<Person>> ptPer=  Optional.of(Optional.of(new Person("!1",2)));
        Optional<Person> innerOptPer = ptPer.flatMap(innerPt ->innerPt);
        //이런식으로 풀수 있음 더풀면 못씀 Optional 을 풀때 Optional 을 포함하고 있지 않다면 동작하지않음
        // 그래서 다중 중첩일때만 flatmap 을 사용함 -> 즉 , 평탄화 작업 말그대로 하고 위해 사용됨
        Person  per2 = innerOptPer.map(person -> person).get();
        // map에서는 래핑된걸 풀지않고 내부값을 변동하기 위해사용 머 예를들어 Optional<Person> -> Optional<String> 이런식으로 내부에선
        // .map(inner->inner.getName())이렇게함
        // 객체만 갖고오고싶으면 마지막에 .get()을 호출하여 해당 객체를 얻어올수 있다.
        String  perStr = innerOptPer.map(person -> person.getName()).get();
        

    }
```

> Optional ::  orElse / orElseGet 사용법
> 

최종적으로 객체가 비어있다면 기본값으로 제공할 객체를 설정하는 기능을 제공

차이점  :  `orElse`의 경우 처음 초기값으로 세팅하는 반면 `orElseGet`의 경우 인자가 비어있는경우에만 초기값 세팅한다.

→ 즉 , `orElse`의 경우 초기값을 지정한 상태에서 받은 인자값으로 치환되는 구조인 반면 , `orElseGet`의 경우 인자를 받지 않는 경우에만 초기값을 세팅하는 구조

```java
public void myOptionalOrElse(){
        Optional<Person> optPer =  Optional.of(new Person("!11", 11 ));
        String reuslt = optPer.map(person -> person.getName()).orElse("Dd");
        String optStr3 = optPer.map(person -> person.getName()).orElse("DefaultName");

        String str = optPer.map(person -> person.getName()).get();
        String str1 = optPer.map(person -> person.getName()).orElse("name");

        // orElse
        Person myPserson1 = Optional.ofNullable(getPsFrOpt()).orElse(new Person("!!",22));
        // 최종적으로 객체가 비어잇다면 기본값으로 제공할 객체를 설정하는 기능

        // orElseGet
        Person myPerson2 = Optional.ofNullable(getPsFrOpt()).orElseGet(()->new Person("!1",22));
        // 마찬가지로 객체가 비어있다면 기본값으로 제공할 객체를 지정하는 기능을 제공 
        // 차이점 : 실제로 객체가 비어있는 경우에만 동작
        //orElse의 경우 인자가 있어도 기본값으로 설정되어있다가 값을 치환하는 느낌이면
        //orElseGet은 실제로 객체가 비어있을때만 초기값을 세팅

    }
```

# 자바의 Stream 클래스

> Stream 클래스 왜 쓰는데
> 

스트림은 java 8 버전 이후로 추가 된 기능으로 람다를 활용할 수 있는 기능 배열 또는 컬렉션 프레임 워크 또는 배열을 다룰 때 사용 배열 또는 컬렉션 인터페이스의 클래스를 여러 개 조합하여 원하는 여러 개 조합

1. 선언 (생성)

2.  가공

3. 반환

```java
public void myStream1(){
        List<String> strList = Arrays.asList("A","B","C");
        List<String> strList1 = Arrays.asList("D","E","F");
        // 동기 모드
        Stream<String> combinedStream =  Stream.concat(strList.stream(),strList1.stream());
        Stream<String> combinedStream1 =  Stream.concat(strList.stream(),strList1.stream());
        // 병렬 처리 모드
        Stream<String> combinedStreamAsync =  Stream.concat(strList.stream(),strList1.stream().parallel());
        Stream<String> combinedStreamAsync1 =  Stream.concat(strList.stream(),strList1.stream().parallel());
        // 가공
        Stream<String> filteredStream = combinedStream.filter(value->value.contains("A"));
        Stream<String> filteredStream1 = combinedStream.filter(value->value.startsWith("A"));
        Stream<String> upperedStream = combinedStream.filter(value->value.contains("A")).
                filter(value->value.contains("B")).map(String::toUpperCase);
        // 반환
        List<String> resultList = upperedStream.toList();
        List<String> resultList1 = upperedStream.collect(Collectors.toList()); //같은코드

        for (int i = 0; i < combinedStream.count(); i++) {
            System.out.println(i);

        }// 이거대신에
        combinedStream.forEach(System.out::println);
        combinedStream.forEachOrdered(System.out::println); // 병렬 처리 모드  일때 순서 보장
        // 이거

        int[] arrays = {1,3,2,4,5,6};
        Arrays.parallelSort(arrays);
    }
```

> 스트림 생성 방법
> 

스트림을 생성하기 위해서는 먼저 인스턴스를 생성하는 과정이 필요

스트림을 만드는 여러 방법

- 컬렉션 스트림
- 기본 타입형 스트림
- 문자열 스트림
- 병렬 스트림 (Parallel Stream)
- 스트림 연결하기
- Stream.builder()
- Stream.generate()
- Stream.iterate()
- 나머지

> 기본 타입 스트림
> 

기본 타입의 변수를 다루기 위해서 스트림에서 기본 타입의 스트림을 제공 필요에 따라 래핑하여 스트림을 생성가능 

```java
 public void PrimaryStream(){
        IntStream intStream = IntStream.range(1,5); //1~4
        LongStream longStream = LongStream.range(1,5); //1~5
        intStream.forEach(System.out::println);
        longStream.forEach(System.out::println);
        Stream<Integer> boxedStream = IntStream.range(1,5).boxed();
        // 해당 IntStream으로 된 요소를 Stream으로 반환하며 각각은 정수로 박싱된다 중간작업
    }
```

> 문자열 스트림
> 

```java
   public void strStream(){
        IntStream intStream = "intStreamMyStream".chars();
        intStream.forEach(System.out::println);
        // 문자열의 각 문자를  IntStream으로 변환
        Stream<String> stringStream = Pattern.compile(" ").splitAsStream("a b c d");
        // regex 패턴을 통해 문자열을 자르고 스트림으로 생성
        stringStream.forEach(System.out::println);

    }
```

> 컬렉션 객체 스트림
> 

```java
 public void collectionStream(){
        String[] array= new String[]{"1","2","3"};

        Stream<String> stringStream  = Arrays.stream(array);
        Stream<String> partitionStream = Arrays.stream(array,1,4);
        List<String> list1 = stringStream.toList();
        List<String> list2 = Arrays.asList("a","b","c");
        Stream<String> stream2 = list2.stream();

        Stream<String> stream3 =  list2.stream();
        Stream<String> stream4 =  list2.parallelStream();

    }
```

> 스트림 병합
> 

```java
 public void concatStream(){
        Stream<Integer> m_Stream= Stream.of(1,2,3);
        Stream<Integer> m_Stream1= Stream.of(4,5,6);

        Stream<Integer> mergedStream = Stream.concat(m_Stream1,m_Stream);
        mergedStream.forEach(System.out::println);

    }
```

> builder , generate , iterate
> 

`builder` 메서드를 사용하여 값을 직접 주입하여 생성할 수 있다.

```jsx
  public  void builderStream(){
        Stream<String> builderStream= Stream.<String>builder().add("1").add("2").add("3").build();
        builderStream.forEach(System.out::println);
        Stream<Integer> builderStreamInt=  Stream.<Integer>builder().add(1).add(2).add(3).build();
        builderStreamInt.forEach(System.out::println);
    }
```

`generate` 메서드를 통해 스트림을 생성할 수 있다. (`Supplier`)

**`generate`** 메서드는 **`Supplier<T>`** 함수형 인터페이스를 인자로 받습니다. **`Supplier<T>`**는 매개변수가 없고 반환값만 있는 함수형 인터페이스입니다. 이것은 일종의 공급자로 생각할 수 있는데, 함수를 호출할 때마다 새로운 값을 생성하여 제공합니다. 따라서 **`generate`** 메서드를 사용할 때 주어진 **`Supplier<T>`** 구현체(일반적으로는 람다 표현식)는 요소를 생성하는 데 사용됩니다. 

```jsx
  public void generateStream(){
        Stream<String> genStream =  Stream.generate(()->"test generate").limit(5);
        // test generate 를 가지는 객체 5개 생성
        genStream.forEach(System.out::println);
        Stream<Person> personGen = Stream.generate(()->new Person("!1",2)).limit(5); // 반복생성자
        personGen.forEach(System.out::println);
    }
```

> Iterate 메서드
> 

`Iterate` 메서드를 통해 스트림을 생성할 수 있다.

`Stream.iterate` 메서드는 지정된 초깃값으로 시작하여 주어진 연산을 계속 반복 적용하여 스트림을 생성하는 데 사용됩니다. 주어진 초깃값으로 시작하여 각 요소를 이전 요소에 적용된 함수로 생성합니다.**`.iterate`** 메서드의 두 번째 매개변수인 **`UnaryOperator`**는 이전 요소를 새로운 요소로 변환하는 람다 함수입니다. 

```jsx
 public void iterateStream(){
        Stream<String> iterateStream= Stream.iterate("A",n->n+"B");
        Stream<Integer> iterateInt = Stream.iterate(new Random().nextInt(1,5),n->n ).limit(5);
    }
```

# 스트림의 가공 방법

> 스트림을 사용하면서 중간 처리를 위한 메소드는 대표적으로 다음과 같다.
> 
- `filter` : 스트림의 요소들을 필터링 하기 위해 사용
- `peek`  :  스트림의 요소들을 최종 처리 전에 수행된 결과를 확인할 수 있음.   Consumer를 파라미터로 사용되며 , 스트림 결과에 영향을 주지 않음.  (`peek` 메서드는 `Consumer`를 인자로 받는다  
또한, `Consumer`는 함수형 인터페이스이며 함수형 인터페이스는 단하나의 추상 메서드를 가지며 람다식이나 메서드 참조를 통해 인스턴스화가 가능 )
- `map` :  스트림 내 요소들을 특정 값으로 변환하는 작업을 수행
내부적으로 function을 사용하고 있으며 스트림 요소를 파라미터로 받아 로직을 수행한 결과로 리턴함
- `sorted` :  스트림 내 요소들을 정렬하는 작업을 수행 
Sorted를 사용하면 Comparable 인터페이스가 구현되어야 함.

```java
public void filterStream(){

        ArrayList<String>  strList = new ArrayList<String>();
        strList.add("!");
        Stream<String> filtStream =  strList.stream();

        Stream<String> filteredStream = filtStream.filter(value->value.contains("!"));
        filteredStream.filter(val -> val.contains("!")).findFirst().ifPresent(System.out::println);
        Stream<String> peekStream =  filtStream.peek(value -> value.contains("!"));
        peekStream.filter(val->val.contains("!")).peek(vo-> System.out.println(vo)).forEach(System.out::println);

        Stream<String> mapStream = filtStream.map(String::toUpperCase);
        Stream<String> result = mapStream.parallel().map(String::toLowerCase);
        result.forEach(System.out::println);

        Random rand = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(rand.nextInt());
        }
        Stream<Integer> sortStream  =  list.stream().sorted();
        sortStream.forEach(System.out::println);
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person per= new Person("name" , i);
            personList.add(per);
        }
        Stream<Person> personStream  = personList.stream();
        Stream<Person> sortedPrStream = personStream.filter(per-> Objects.equals(per.name, "name")).sorted();
        Stream<String> personName = personStream.map(per->per.getName());
        Stream<Person> peekPerson  = personStream.peek(per->per.getName().equals("name"));
    }
```

# 스트림 최종 연산 방법

스트림에서 제공하는 최종 연산의 종륜는 아래와 같다. 

- `연산`  
count , min , max , average 와 같은 메서드가 있다 .
- `수집`
Collect 메서드를 사용하며 , Collectors 메서드를 파라미터로 받아 처리 
일반적으로 Collector를 활용할 수 있는 객체를 사용해야 하며 , 임의로 Collector 인터페이스를 구현하여 활용할 수 있음 많이 사용되는 메소드는 별도로 Collect 메서드를 사용하지 않고 사용할 수 있음
- `검사`  
스트림의 요소 중 조건을 만족하는 요소가 있는지 검사하기 위한 메서드를 아래와 같이 제공 
Predicate를 받아 조건을 검사함
    
    `anyMatch`   : 하나라도 만족하는 요소가 있는지  → 즉 , 리스트에서 하나라도 만족을 하는지 
    `allMatch` :  모두 조건을 만족하는지 → 즉,  리스트에서 모두 만족을 하는지 
    `noneMatch`  : 모두 조건을 만족하지 않는지  → 즉, 리스트에서  
    
- 소모
Reduce 메서드는 파라미터를 총 3개를 받을 수 있다.

reduce 메서드의 파라미터의 총 3가지를 받을 수 있다.
- accumulator : 각 요소를 처리하는 계산 로직 . 요소가 나올때마다 중간결과를 생성 
(`인자가 1개인 경우` )
- identity :  계산을 위한 초기값으로 스트림이 비어서 계산할 내용이 없더라도 리턴 
(`인자 2개일 경우 추가됨`)
- combiner : 병렬 스트림에서 나눠 계산한 결과를 하나로 합칠 떄 사용 
(`인자 3개일 경우 추가됨` )

```java
  public void StreamReduce(){
        List<Integer> numbs =  List.of(1,2,3,4,5);
        int sum   =  numbs.stream().reduce((x,y)->x+y).get(); // get을 안하면 Optional 로 반환함
        // optional 을 반환하는 이유 : reduce 메서드는 스트림이 비어있을때를 대비해서 Optional 을 반환함
        // 인자가 하나인 경우  :  누산기  -  각 요소를 처리하는 계산 로직
        int sum2 =  numbs.stream().reduce(Integer::sum).get();

        List<String> strs =  List.of("1","2","3");

        // 인자가 두 개 인 경우   identity (초기값)  ,  accumulator
        int sum3 =  numbs.stream().reduce(0,(x,y)->x +y);
        // identity :  계산을 위한 초기값으로 스트림이 비어서 계산할 내용이 없더라도 리턴 (인자 2개일 경우 추가됨 )

        // 인자가 세 개인 경우  combiner
        int sum4 = numbs.stream().reduce(0,Integer::sum, Integer::sum);
        int sum5 = numbs.stream().reduce(0,(x,y)->x+y, Integer::sum);
        // combiner : 병렬 스트림에서 나눠 계산한 결과를 하나를 합칠때 사용
        // 3번째 인자가 병렬 스트림 combiner 

    }
```

> 어노테이션
> 

`@RestController , @GetMapping(”hello”)`

프로그래밍에서 주석은 개발자의 입장에서 더 직관적이고 코드를 이해하기 쉽게 하며, 다른 사람에게 설명할 수 있도록 정보를 제공하는 역할이다

- 이노테이션이란 ?
1. 컴파일러에게 문법 에러를 체크하도록 정보를 제공
2. 프로그램 빌드할 떄 코드를 자동으로 생성할 수 있도록 정보를 제공한다.
3. 런타임에 특정 기능을 실행하도록 정보를 제공한다.

`이노테이션은 @를 사용하여 작성하며 ,  해당 타겟에 대한 동작을 수행한는 프로그램 외에는 다른 프로그램에게 영향을 주지 않는다.` 

어노테이션의 종류어노테이션은 크게 세 가지로 구분된다. 자바에서 기본적으로 제공하는 표준 어노테이션과 어노테이션을 정의하는 데 사용되는 메타 어노테이션, 마지막으로 사용자 어노테이션이 있다.표준 어노테이션자바에서 기본적으로 제공하는 어노테이션이다.

`@Override`  : 컴파일러에게 메서드를 오버라이딩하는 것이라고 알린다.

`@Deprecated` : 앞으로 사용하지 않을 대상임을 알린다.

`@FunctionalInterface` : 함수형 인터페이스라는 것을 알린다.

`@SuppressWarning` : 컴파일러가 경고 메시지를 나타내지 않는다.

`@SafeVaragrs` : 제네릭과 같은 가변 인자의 매개변수를 사용할 때의 경고를 나타내지 않는다.메타 어노테이션어노테이션에 붙이는 어노테이션으로, 어노테이션을 정의하는 데 사용한다.

`@Targe` : t어노테이션을 정의할 때 적용 대상을 지정하는 데 사용한다.

`@Documented` : 어노테이션 정보를 javadoc으로 작성된 문서에 포함시킨다.

`@Inherited`  : 어노테이션이 하위 클래스에 상속되도록 한다.

`@Retention` : 어노테이션이 유지되는 기간을 정하기 위해 사용한다.

`@Repeatable` : 어노테이션을 반복해서 적용할 수 있도록 한다. 

# Spring Boot 왜 쓰는데

**서버를 만들 수 있는 프레임워크는 수백개가 있습니다.** 

빠른 비동기처리 서버를 만들고 싶으면 Node.js가 편하고

AI 서비스에 붙일 서버가 필요하면 Python이 편하고

그냥 리액트가 좋으면 Next.js 쓰면 되고

성능과 동시성 좋아하면 Erlang류, Rust, Go 이런걸 쓰면 되는데

근데 밥벌어먹으려면 어쩌겠습니까 한국에서 가장 많이 쓰는 Spring Boot 해야 취업길이 조금이라도 더 넓습니다.

> **안어려움**
> 

Spring Boot는 서버 만드는 프레임워크입니다.

스프링 자체가 코드 배치만 하면 뭔가 마법처럼 딱딱 돌아가기 때문에

코드 짜는 법 잘 몰라도 기술처럼 외워쓸 수 있습니다.

그게 장점이자 단점입니다.

프레임워크의 특성상 그냥 코드 따라적기만 해도 마법처럼 동작하니까

복붙식으로 가르치는 강의들이 아직도 많습니다.

그런 이상한 강의 듣고와서 코딩은 기술복붙과 암기라고 오해하고 노잼이라 때려치는 분들도 많은데

하지만 개발의 본질은 '기술복붙'이 아니라 '내가 원하는 소프트웨어를 만들어내는 짓' 아니겠습니까

그런 개짓거리를 해보고 싶다면 여기서 개발자 식으로 배워보도록 합시다.

> **쇼핑몰 프로젝트**
> 

쇼핑몰을 만들어봅시다.

근데 그냥 쇼핑몰이라는 껍데기일 뿐이고 실은 그냥 게시판임

왜 게시판만드냐면 웹에 있는 거의 모든 기능은 90% 확률로 게시판이랑 똑같습니다.

당근마켓도 그냥 물건사진 올리는 게시판이고

인스타그램도 친구게시물 보여주는 게시판이고

쇼핑몰도 상품사진 올리는 게시판이고

심지어 이거 쇼핑몰에서 주문버튼 누르는 것도 게시판 글발행하는거랑 똑같고

그래서 게시판 만들 줄 알면 모든 종류의 웹사이트 알아서 전부 만들 수 있습니다.

> **AI 시대엔 코딩 방법이 다름**
> 

GPT 이후로 온갖 코딩 AI가 나오고 있는데

기술 외워서 그대로 배설하는건 AI가 훨씬 더 잘하기 때문에 그런 식으로 배우면 아무 쓸모없는 인간이 되어

AI의 바이오 연료로 전락할 뿐입니다.

- 이 라이브러리는 왜 쓰는지
- 이 코드는 왜 넣어야하는 건지
- 대체 이런 구조로 만드는 이유가 뭔지

등의 큰 그림을 이해하는게 훨씬 중요해서 그런걸 주로 알려드리는 강의입니다.

그래야 AI에게 채찍질을 할 수 있는 사람이 됩니다.

> **필요한 사전지식**
> 

1. html css 아주 조금

2. 변수, 함수, for, if, array/object (List/Map) 자료형

이런 프로그래밍 기초지식이 있으면 됩니다.

2번은 자바말고 자바스크립트로 알고있어도 상관없습니다.

어짜피 프로그래밍 언어 문법들은 다 비슷합니다.

웹개발 하는데 자바스크립트 모르면 안되기 때문에 오히려 자바스크립트가 나을 수도 있습니다.

class 문법이나 객체지향 그런건 몰라도 됩니다.

# Spring (스프링)  VS Spring Boot(스프링 부트)

> **스프링(Spring) 이란?**
> 
- 스프링 - 프레임 워크

스프링 프레임 워크는 자바에서 가장 많이 사용되는 프레임워크이다. 

`의존성 주입(DI, Dependency Injection)`과 `제어역전(IOC, Inversion of Control)`, `관점 지향 프로그래밍(AOP)`이 가장 중요한 요소

위 요소들을 통해 느슨한 `결합(Loose Coupling)`을 달성할 수 있음

위와 같이 느슨한 결합으로 개발한 어플리케이션은 단위 테스트를 수행하기 용이함.

> **의존성 주입**
> 

```abap
 @RestController 
 public classs NoController{
 
	 private MyService service = new MyServicelmp();
	 @GetMapping("/hello");
	 public String getHello(){
		 return service.getHello();
	 }
 }
```

예제 코드와 같이  DI를 사용하지 않는 코드의 경우 , 객체의 인스턴스를 얻게 되면 객체 간의 결합도가 올라가게 된다. 이런 코드 작성은 단위 테스트를 위해 Mock 객체를 사용할 수 없게 됨.

> **의존성 주입이란 ? ?**
> 

> **DI가 먼데 ?**
> 

`DI`는 `Dependency` `Injection`의 약자로, 객체 간의 `의존` `관계를` 외부에서 주입하는 디자인 패턴입니다. 객체가 다른 객체를 사용하기 위해서 본인의 내부 로직에서 외부 객체에 대한 인스턴스를 생성하는 것이 아닌, 외부로부터 인스턴스를 주입받아 사용하는 것을 의미합니다. DI는 생성자 주입과 필드주입이 존재하며, 대체로 외부에서 변경이 가능한 생성자 주입을 사용하는 것을 권고합니다.

- `DI 를 사용하면 생기는 일`

```java
// Dependency Injection을 사용한 예시

// 외부에서 주입받을 인터페이스
interface MessageService {
    void sendMessage(String message);
}

// 의존성을 주입받는 클래스
class Client {
    private final MessageService messageService;

    // 생성자 주입을 사용하여 외부에서 의존성을 주입받음
    public Client(MessageService messageService) {
        this.messageService = messageService;
    }

    public void processMessage(String message) {
        // 외부에서 주입받은 의존성을 사용
        messageService.sendMessage(message);
    }
}

// 의존성 주입 예시
public class Main {
    public static void main(String[] args) {
        // 의존성을 주입하여 Client 인스턴스 생성
        MessageService messageService = new EmailService();
        Client client = new Client(messageService);
        client.processMessage("Hello, DI!");
    }
}
```

- `DI 를 사용하지 않으면 생기는 일`

```java
// DI를 사용하지 않는 예시

// 의존성을 직접 생성하여 사용하는 클래스
class Client {
    private final MessageService messageService = new EmailService(); // 직접 생성

    public void processMessage(String message) {
        // 직접 생성한 의존성을 사용
        messageService.sendMessage(message);
    }
}

// 의존성 주입을 사용하지 않은 예시
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.processMessage("Hello, DI!"); // 이 부분에서 직접 생성된 의존성 사용
    }
}

```

이래도 모르겠다구요? 간단하게 설명해드리겠습니다.

`DI를 사용하는 경우` 

```java
private final Book book;

public Study(Book book){
	this.book = book;
}
```

`DI를 사용하지 않는 경우` 

```java
private final Book book;

public Study(){
	this.book = new Book();
}
```

> **DIP가 먼데?**
> 

DIP란 원칙인데요 객체에서 어떤 Class를 참조해서 사용해야 하는 상황이 생긴다면 그 Class를 직접 참고하는 것이 아니라 그 대상 상위 요소 (추상 클래스 OR 인터페이스)로참조하는 원칙입니다. 

클래스 간 의존 관계란, 한 클래스가 어떤 기능을 수행하려고 할 때, 다른 클래스의 서비스가 필요한 경우를 말한다.대표적으로 A 클래스의 메소드에서 매개변수를 다른 B 클래스의 타입으로 받아 B 객체의 메서드를 사용할때, A 클래스는 B 클래스와 의존한다고 보면 된다. 
 

```java
interface MessageInterface{
	String getMessage();
}
// 고수준 모듈이 의존하는 추상화 된 인터페이스 
class MyMessageApp{
	private final MessageInterface messageinit;
	
	public MyMessageApp(MessageInterface messsageinit){
		this.messageinit = messageinit;
	}
	public void sendMessage(){
		String initMessage =this.messageinit.getMessage();
		System.out.println(initMessage);
	}
}

// 저수준 모듈
class EmailMessage implements MessageInterface(){
	@Override
	public String getMessage(){
		return "이메일 메시지"
	}
}
// 저수준 모듈
class SmsMessage implements MessageInterface(){
	@Override
	public String getMessage(){
		return "SMS 메시지";
	}
}

public class Main{
	MessageInterface messageinit = new EmailMessage();
	MyMessageApp myApp  = new MyMessageApp(messageinit) 
	myapp.getMessage();
	
	MessageInterface messageinit2 = new EmailMessage();
	MyMessageApp myApp2  = new MyMessageApp(messageinit2) 
	myapp2.getMessage();
	**
}
```

**MessageInterface 타입의 매개변수를 받아들이기 때문에, 어떤 클래스가 이 인터페이스를 구현하고 있더라도 해당 클래스의 객체를 생성자에 전달할 수 있습니다.**

기본적으로 DI는 다음과 같은 원칙을 따릅니다:

1. **`의존성` `주입`**: 객체가 필요로 하는 의존성을 외부에서 주입받습니다. 이를 통해 객체는 직접 의존 객체를 생성하거나 검색하는 것이 아니라 외부에서 주입된 의존성을 사용합니다.
2. **`인터페이스에` `의존`**: 객체가 구체적인 구현체가 아닌 인터페이스나 추상 클래스에 의존하도록 설계합니다. 이를 통해 객체는 유연성이 높아지며, 다양한 구현체를 주입받아 사용할 수 있습니다.
3. **`제어` `역전`**: 의존성의 관리가 외부에서 이루어지므로 객체는 자신이 사용할 의존성을 직접 결정하지 않습니다. 대신 외부에서 객체에 필요한 의존성을 주입해줍니다.

`DI 의존성을 사용하는 경우` 

```java
interface MessageService {
    void sendMessage(String message);
}

class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        // 이메일을 보내는 로직
    }
}

class SMSService implements MessageService {
    @Override
    public void sendMessage(String message) {
        // SMS를 보내는 로직
    }
}

```

```java
MessageServiceClient client1 = new MessageServiceClient(new EmailService());
client1.sendMessage("This is an email message");

MessageServiceClient client2 = new MessageServiceClient(new SMSService());
client2.sendMessage("This is an SMS message");
```

`DI 의존성을 사용하지 않는 경우` 

```java
class MessageServiceClient {
    private MessageService messageService;

    // 생성자를 통해 의존성 주입
    public MessageServiceClient(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendMessage(String message) {
        messageService.sendMessage(message);
    }
}

```

```java
class EmailServiceClient {
    private EmailService emailService = new EmailService();

    public void sendEmailMessage(String message) {
        emailService.sendMessage(message);
    }
}

```

`⇒ 이렇게 의존성 강하게 하면 코드의 유연성과 재사용성 감소하고 수정하기 힘듭니다. 혼납니다.`

> **관점 지향 프로그래밍 (AOP )**
> 

AOP는 쉽게 OOP를 보완하는 수단으로써 ,  여러 곳에서 쓰이는 공통 기능을 모듈화 하여 필요한 곳에 연결함으로쏘 유지보수 또는 재사용에 용이하도록 하는 것을 의미.

AOP를 통해 기존 프로젝트에 다양한 기능을 로직 수정없이 추가할 수 있음

⇒ 즉, 관점이라는 단위로 프로그램을 모듈화하는 프로그래밍 패러다임입니다. 이것은 흩어진 관심사(concern)들을 분리하고, 각각을 모듈화하는것. 

> **스프링 부트가 나오게 된 이유**
> 

스프링 부트는 단지 실행만 하면 되는  스프링 기반의 어플리케이션을 쉽게 만들 수 있다.  

스프링은 다양한 기능을 제공하고 있지만 , 그 기능을 사용하기 위한 설정에 많은 시간이 걸림 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/3e2f9225-6ffb-4be1-aa17-b90302bcab24.png)

 

```xml
<!-- applicationContext.xml -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 데이터베이스 연결 정보를 포함하는 dataSource 빈 설정 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
        <property name="username" value="user"/>
        <property name="password" value="password"/>
    </bean>

</beans>
	
```

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("User Name: " + rs.getString("name"));
                System.out.println("User Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

```

스프링의 DataSource 빈은 XML 파일에서 설정되어 있고, 해당 빈은 스프링 컨테이너에 의해 관리됩니다. XML 파일에서 지정된 속성들(드라이버 클래스, URL, 사용자 이름, 암호 등)은 DataSource 빈을 생성할 때 사용되어 데이터베이스에 대한 초기 연결을 수립합니다.

즉, UserDao 클래스에서 DataSource 빈을 주입받으면 스프링이 ApplicationContext.xml에 정의된 설정을 사용하여 데이터베이스와의 연결을 수행합니다. UserDao 클래스는 직접 데이터베이스 연결에 대한 로직을 작성할 필요가 없습니다. 이렇게 함으로써 설정 정보를 외부에 분리하여 유연성을 확보할 수 있습니다.

> Spring 을 쓰는 경우 VS Spring Boot 를 쓰는 경우
> 

`Spring 을 쓰는 경우` 

```xml
<!-- applicationContext.xml -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 데이터베이스 연결 정보를 포함하는 dataSource 빈 설정 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
        <property name="username" value="user"/>
        <property name="password" value="password"/>
    </bean>

</beans>

```

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("User Name: " + rs.getString("name"));
                System.out.println("User Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

```

`Spring Boot 를 쓰는 경우`  

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        return dataSource;
    }

}

```

# Spring IOC 컨테이너(**Inversion of Control)**

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%205.png)

`IoC 제어`의 역전이란 `Inversion of Control`의 약자로, 프로그램의 제어를 다른 대상이 맡는 것을 말한다.

스프링의 경우에는 스프링 컨테이너가 Bean의 생성, 의존관계주입과 같은 작업을 담당한다.

여기서 스프링 컨테이너는 `ApplicationContext`이며, `IoC` 컨테이너 혹은 `DI` 컨테이너라고도 부릅니다.

스프링은 포함한 프레임워크는 내가 작성한 코드를 제어하고, 대신 실행한다.

`스프링 컨테이너 (= IoC 컨테이너)`의 종류

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%206.png)

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%207.png)

스프링 컨테이너가 관리하는 객체를 `빈(Bean)`이라고 하고,이 빈들을 관리한다는 의미로 컨테이너를빈 `팩토리(BeanFactory)` 라고 부릅니다.객체의 생성과 객체 사이의 런타임 관계를 DI 관점에서 볼 때 컨테이너를 `BeanFactory`라고 한다.`BeanFactory`에 여러가지 컨테이너 기능을 추가한어플리케이션컨텍스트(`ApplicationContext`)가 있다.`BeanFactory`와 `ApplicationContext1`.  `BeanFactory`- `BeanFactory` 계열의 인터페이스만 구현한 클래스는 단순히 컨테이너에서 객체를 생성하고 DI를 처리하는 기능만 제공한다.- Bean을 등록, 생성, 조회, 반환 관리를 한다.- 팩토리 디자인 패턴을 구현한 것으로 BeanFactory는 빈을 생성하고 분배하는 책임을 지는 클래스이다.- Bean을 조회할 수 있는 `getBean()` 메소드가 정의되어 있다.- 보통은 `BeanFactory`를 바로 사용하지 않고, 이를 확장한 ApplicationContext를 사용한다.2.  ApplicationContext- Bean을 등록, 생성, 조회, 반환 관리하는 기능은 BeanFactory와 같다.- 스프링의 각종 부가 기능을 추가로 제공한다.BeanFactory 보다 더 추가적으로 제공하는 기능국제화가 지원되는 텍스트 메시지를 관리 해준다.이미지같은 파일 자원을 로드할 수 있는 포괄적인 방법을 제공해준다.리스너로 등록된 빈에게 이벤트 발생을 알려준다.

말 그대로 제어가 역전이 되어서 객체에 대한 생명주기 , 관리 등을 프레임워크 내부에서 관리하는 방식을 말합니다.

# Controller

서버가 뭐냐면

서버가 뭐냐면 그냥 유저가 데이터 요청하면 그 데이터 보내주는 따까리 같은 프로그램일 뿐입니다.

이거 해줘 그러면 진짜 그거 해주는 프로그램이 서버일 뿐임

Q. 유튜브 서버가 뭐임?

- 누가 동영상달라고 요청하면 동영상 보내주는 프로그램일 뿐입니다.

Q. 네이버 웹툰 서버가 뭐임?

- 누가 웹툰달라고 요청하면 웹툰보내주는 프로그램일 뿐입니다.

Q. 웹서버는 뭐임?

- 그냥 누가 웹페이지 달라고 하면 웹페이지 보내주는 서버일 뿐입니다.

그래서 서버개발이 어려운게 아니고

누가 메인페이지달라고 하면 메인페이지 보내주고

로그인페이지 달라고 하면 로그인페이지 보내주고

그런 식으로 코드짜면 웹서버개발 끝입니다.

실은 글 저장 수정 삭제 이런것도 처리해주는 기능도 있을 수 있는데 이런 것들은 나중에 해보고

이번 시간엔 간단하게 웹페이지 보내주는 서버와 그 기능부터 만들어봅시다.

서버기능 만들려면 Controller

일단 코드짜게 .java 파일을 아무데나 새로 만들어봅시다.

BasicController.java 라고 맘대로 작명해봤습니다.

```java
(BasicController.java)

package [com.apple.shop](http://com.apple.shop/);

@Controller
public class BasicController {

}
```

클래스는 에디터에서 알아서 생성해줍니다.

여기다가 이제 서버기능만들면 됩니다.

서버기능을 만들고 싶으면 아무 클래스에 @Controller 써놓고 시작하면 됩니다.

 
@GetMapping() 안에 페이지 경로 마음대로 적고

```java
@Controller
public class BasicController {
@GetMapping("/경로")
@ReponseBody
String hello(){
return "유저에게 보내줄데이터";
}
}
```

@ResponseBody 쓰고

return 오른쪽에 유저에게 보내줄 데이터를 적으면

이제 /경로로 방문했을 때 그 데이터를 보내줍니다.

참고로 @ 붙은걸 작성할 땐 엔터키 잘 쳐서 상단에서 import 해와야 동작합니다.

Q. 왜 그래야함?

- Spring boot 프레임워크 사용법일 뿐이라 이렇게 쓸 뿐입니다.

골뱅이 잘 넣으면 알아서 서버기능이 되도록 만들어놓은 프레임워크임

심지어 골뱅이만 잘 붙이면 main 함수에 안넣어도 알아서 제때 잘 실행해줍니다.

 
그래서 저는 누가 / 메인페이지로 접속했을 때

```java
@Controller
public class BasicController {
@GetMapping("/")
@ReponseBody
String hello(){
return "안녕하세요";
}
}
```

"안녕하쇼"라고 유저에게 메세지를 보내는 기능을 만들어봤습니다.

그래서 프로젝트 실행해본 다음에 브라우저켜서 localhost:8080으로 접속해봅시다.

이게 여러분 메인페이지인데 접속해보면 "안녕하쇼"가 잘 나오는군요.

다른 페이지도 만들어보자

다른 웹사이트들 보면 메인페이지 말고 다른 페이지도 많지 않습니까 예를 들면

[comic.naver.com/webtoon](http://comic.naver.com/webtoon) 으로 접속하면 요일웹툰 볼 수 있는 페이지를 보내주고

[comic.naver.com/challenge](http://comic.naver.com/challenge) 로 접속하면 도전만화 페이지 보내주고요

그런 식으로 URL을 다르게 입력하면 다른 페이지를 각각 보여줍니다.

우리도 이런 식으로 다른페이지하나 만들어봅시다.

예를 들면 누가 /about으로 접속하면 이 사이트 소개글같은거 보내주고 싶으면 어떻게해요?

 

```java
@Controller
public class BasicController {
@GetMapping("/")
@ReponseBody
String hello(){
  return "안녕하쇼";
}

@GetMapping("/about")
@ReponseBody
String hello(){
   return "피싱사이트에요";
}

```

}
새로운 서버 기능이 하나 필요할 때 마다 @GetMapping() 덩어리 복붙해서 쓰면 되겠습니다

> thymeleaf : 자바에서  웹 기반 템플릿을 생성할 수 있는 엔진
> 

타임리프(Thymeleaf)는 자바 라이브러리이며, 웹과 환경 양쪽에서 TEXT, HTML, XML, Javascript, CSS를 생성할 수 있는 템플릿 엔진이다.
스프링 MVC와의 통합 모듈을 제공하며, 애플리케이션에서 JSP로 만든 기능들을 완전히 대체할 수 있다.
즉, 타임리프(Thymeleaf)는 View Templete Engine으로 JSP, Freemarker와 같이 서버에서 클라이언트에게 응답할 브라우저 화면을 만들어주는 역할을 한다.

```java
// build.gradle에 잘 넣어줍니다.
dependencies {
   implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
}// load gradle changese 합시다.
```

> 사용법
> 

```java
@Controller
@RequiredArgsConstructor
public class ItemController {

private final ItemRepository itemRepository;

@GetMapping("/list")
String list(Model model) {
List<Item> result = itemRepository.findAll();
model.addAttribute("name", "비싼 바지");

return "list.html";

}

}
```

# Entity

`Spring` `Boot`와 같은 `Java` 기반 프레임워크에서 "`엔티티`(`Entity`)"는 주로 데이터베이스의 테이블을 나타내는 클래스를 의미합니다. 이 클래스는 `JPA`(`Java Persistence API`)를 사용하여 데이터베이스에 저장되고 관리됩니다

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%208.png)

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%209.png)

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 테이블 생성하려면 @Entity로 생성
@Getter 
@NoArgsConstructor// 기본 생성자 제공
@Entity // 독립체라고 함
@Table(name = "User")
public class User {//해당 이름으로 테이블 하나생성해줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId; // 컬럼용 변수에는 Ineger를 써야함
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    Integer age;

    @Builder
    public User(  String name, Integer age) {

        this.name = name;
        this.age = age;
    }

//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    } @Getter쓰면  쓸필요 X
}

```

이런식으로 하나의 Entity 를 만들면 그게 하나의 테이블입니다. 

# Azure MySQL 유동 서버 호스팅 받기

1. Azure 검색해서 들어가서 가입하고 카드등록까지 마칩니다.

(마이크로소프트 계정으로 로그인 가능)

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2010.png)

1. [portal.azure.com](http://portal.azure.com/) 상단 검색창에서 'MySQL 유동서버' 검색 후 진입

그럼 만들기 버튼이 어딘가 있을텐데 눌러서 만들어봅시다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2011.png)

1. 알아서 잘 채워줍니다.
- 구독/리소스 그룹은 비용관리용 폴더같은 것인데 대충 새로 만들어줍시다.
- 서버이름은 유니크하게 잘 작명하고 한국에서 서비스할거면 Korea 선택합시다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2012.png)

1. 컴퓨팅/스토리지 선택하는 곳이 있을텐데
- 컴퓨터는 무료라고 써있는거 잘 선택하시고
- IOPS는 '미리 프로비전된 IOPS' + 최소사양을 선택해야 아마 추가요금이 없습니다.

360 저건 입출력을 1초에 360회로 제한한다는 뜻이라 나중에 유저 많아지면 '자동확장 IOPS' 이런거 쓰거나 조절해봅시다.

1. DB 접속용 아이디/비번도 만들어줍시다.

털리면 코딩인생 끝나는 것임

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2013.png)

1. 다음 탭 눌러보면 접속가능한 IP주소도 설정할 수 있습니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2014.png)

- 안전하게 하려면 Azure 서버컴퓨터만 접속가능하게 '프라이빗 액세스' 이런걸 고르면 되겠지만

우리는 내 컴퓨터에서 접속해야하니까 '공용 액세스'를 고릅시다.

- 근데 다행히 특정 IP주소만 접속가능하게 보안장치를 하나 만들 수 있는데 여러분 IP주소 기입하면 됩니다.

어짜피 연습용이라 스타벅스에서 코딩할거면 모든 IP주소를 위 사진처럼 추가합니다.

그럼 나머지는 건드릴거 없을거고 데이터베이스 생성하면 됩니다. 대충 5~10분 걸림

1. 생성되었으면 마지막으로 설정할게 하나 있음

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2015.png)

데이터베이스 생성되었으면 들어가보면 '서버 매개 변수' 설정할 수 있는 부분이 어딘가 있을텐데

들어가서 require_secure_transport를 OFF로 설정합시다.

어짜피 연습용인데 SSL 인증같은걸 잠깐 끄는 식입니다.

Database 생성 끝

> AWS는요
> 

AWS를 좋아한다면 RDS 이런거 써도 되는데

올해부터 RDS 사용시 퍼블릭 액세스를 "예"로 설정하면 IPv4 사용요금 명목으로 월 3달러 정도 청구되는 것으로 바뀌어서

AWS EC2 들어가서 인스턴스 하나 만들어서 같은 VPC그룹에 집어넣고 SSH 키파일도 가져온 다음에

SSH 터널링으로 내 컴퓨터 -> EC2 -> RDS DB 이런 식으로 접속하거나

아니면 EC2 인스턴스에 직접 MySQL 설치해서 써야합니다.

하지만 EC2에 설치하면 백업이나 모니터링 같은 것도 직접 해야하기 때문에 귀찮습니다.

# 서버와 MySQL 연동하기

JPA와 MySQL 접속용 라이브러리 설치는

 
build.gradle 파일에 추가하고 load gradle changes 버튼 누르면

```java
dependencies {
runtimeOnly 'com.mysql:mysql-connector-j'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

JPA와 MySQL 접속도와주는 라이브러리가 설치 완료 됩니다.

```java
왜 이건 runtimeOnly라고 쓰냐
 

runtimeOnly라고 적으면 코드를 컴파일할 때는 필요없는 라이브러리니까 컴파일 할 땐 쓰지말라는 소리입니다.

이 라이브러리 만든 사람이 runtimeOnly라고 사용해도 상관없게 만들어놔서 그럴 뿐입니다.

 

컴파일이뭐냐면 자바코드는 원래 실행하기 전에 컴퓨터 친화적인 바이트 코드로 변환해야하는데 그걸 컴파일이라고 부릅니다.

그래서 이렇게 쓰면 컴파일 되는 시간을 절약할 수 있겠군요.

그러기 싫으면 그냥 implementation 써도 상관없습니다.

 

그래서 코드를 실제로 실행할 때만 필요한 라이브러리들은 runtimeOnly만 적어놔도 되는데

DB입출력 도와주는 라이브러리나 로그 출력용 라이브러리들이 이런걸 써도 됩니다.

나머지는 쓸 일이 없음

 

아니면 반대로 compileOnly 라고 적으면 얘는 컴파일할 때만 쓰라는 뜻입니다.

그러면 개발할 때만 잠깐 쓰고 실제 서버구동시 필요없는 라이브러리들은 compileOnly 집어넣으면 나중에 용량을 절약할 수 있습니다.

나중에 써볼 것인데 코드 자동완성을 시켜주는 Lombok 라이브러리는 그런 식으로 설치해서 쓰기도 합니다.
```

```java
spring.datasource.url=jdbc:mysql://호스팅받은곳엔드포인트주소/만든database이름
spring.datasource.username=DB접속아이디
spring.datasource.password=DB접속비번
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.hibernate.ddl-auto=update 
```

application.properties 파일에 추가하면 서버에서 MySQL 데이터베이스에 접속이 가능합니다.

> Supabase 등에서 PostgreSQL 호스팅받아서 사용할 경우
> 

```java
dependencies {
  runtimeOnly 'org.postgresql:postgresql'
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

build.gradle 파일에 추가하고 load gradle changes 버튼 누르면

JPA와 PostgreSQL 접속도와주는 라이브러리가 설치 완료 됩니다.

```java
spring.datasource.url=Supabase에 있던 Connection string
spring.datasource.username=Supabase에 있던 User
spring.datasource.password=Supabase에 있던 Password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.hibernate.ddl-auto=update 
```

Supabase 설정 들어가보면 JDBC버전의 Connection string, User, Password 정보를 가져올 수 있습니다.

그거 가져다가 application.properties 파일에 입력합시다.

Connection string 복붙할 때 패스워드 넣는 부분이 있으면 넣어줍시다.

# Lombok 설치하기

```java
dependencies {
  compileOnly 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok'
}

```

build.gradle 파일에 이런걸 추가하면 Lombok 라이브러리를 설치할 수 있습니다.

그리고 셋팅이 좀 필요합니다.

1. 에디터 상단메뉴에 파일 - Settings - Plugin 메뉴 들어가서

Lombok이라는 플러그인도 설치해야하고

2. 에디터 상단메뉴에 파일 - Setting 메뉴에서 annotation processor 검색해서 체크해둬야 쓸 수 있습니다.

`Lombok이란 어노테이션 기반으로 코드를 자동완성 해주는 라이브러리이다. Lombok을 이용하면 Getter, Setter, Equlas, ToString 등과 다양한 방면의 코드를 자동완성 시킬 수 있다.`
 

## 서버에서  DB 데이터를 출력하려면

Entity 는 즉 테이블이에요. 이걸 Controll 하는 곳은 Repository라고 합니다.

> Repository 만들기
> 

```java
package com.example.demo.repository;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
                                                    //< Entity 명 ,id 컬럼타입>
public interface UserRepository  extends JpaRepository<User, Integer> {
}
//클래스 상속받은 인터페이스면 됩니다.
```

> Entity 다시 보기
> 

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 테이블 생성하려면 @Entity로 생성
@Getter
@NoArgsConstructor// 기본 생성자 제공
@Entity // 독립체라고 함
@Table(name = "User")
public class User {//해당 이름으로 테이블 하나생성해줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId; // 컬럼용 변수에는 Ineger를 써야함
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    Integer age;

    @Builder
    public User(  String name, Integer age) {

        this.name = name;
        this.age = age;
    }

//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    } @Getter로 쓸필요 X
}

```

> Controller 에 사용할 해당 Repository 등록
> 

```java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 내부 상세 페이지를 담당
@Controller
@RequestMapping(value = "/Details") // 이렇게하면 기본값이 되는 엔드 포인트가 됨
@RequiredArgsConstructor
// Getmaaping이전의 Method 를 정의해야 하는 이전의 방법이지만 Method를 정의하지 않으면 내부에서
// Getmapping , DeleteMapping 등 모두 다 사용가능
public class DetailController {
    private final UserRepository userRepository;

//    @Autowired
//    public DetailController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    } // @RequiredArgsConstructor 의 역할이에요 인자가 필요한 생성자를 정의해줍니다.
// lombok의 어노테이션 메서드 사용하기 싫으면 이렇게하면됩니다.

    @GetMapping("/detailsd")
    public String detailsd(Model model) {
        return "index.html";
    }
    @GetMapping("/detailsa")
    public String detailsa(Model model) {
        // User 테이블 꺼내주세요

        String DBdata =  "서버에서 데이터에요";
        model.addAttribute("title", DBdata);
        // 첫번째 인자 변수 , 두번째 인자 넣을 데이터
        return "home.html";
    }
    @GetMapping("/detailsb")
    public String detailsb(Model model) {
        // User 테이블 꺼내주세요
        User userProxy=userRepository.getReferenceById(1); // ID 값이 1인 유저를 가져옵니다.
        Integer age= userProxy.getAge();
        String name = userProxy.getName();
        model.addAttribute("title", age+name);
        // 첫번째 인자 변수 , 두번째 인자 넣을 데이터
        return "home.html";
    }
    @GetMapping("/GetAll")
    public String GetAll(Model model) {
        List<User> userProxy = userRepository.findAll();
        List<String> nameList = new ArrayList<>();
        List<Integer> ageList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();

        for (User user: userProxy ){
            nameList.add(user.getName());
            ageList.add((user.getAge()));
        }
        if (nameList.size() == ageList.size()){
            for (int i = 0 ; i<nameList.size(); i++){
                map.put(nameList.get(i),ageList.get(i));
            }
        }
        model.addAttribute("title", map);
        return "index.html";
    }

}
ㅊ
```

사용자 정의 findBy 메서드 사용하고 싶으면 이런식으로 구성하면 끝입니다.

```java
package com.example.demo.repository;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
                                                    //< Entity 명 ,id 컬럼타입>
public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByName(String username);
    User findByAge(int age);
}

```

스프링 데이터 JPA에서 제공하는 기본적인 쿼리 메서드의 예시입니다. 이러한 메서드들을 정의하면 스프링 데이터 JPA가 메서드 이름을 분석하여 해당하는 쿼리를 자동으로 생성해줍니다.

이렇게 말이에요 

# 서버에서 받은 DB 데이터를 HTML에 넣으려면

이번엔 html에 타임리프 문법을 통해 서버 데이터를 타임리프(Thymeleaf)를 넣을수 있습니다.

리액트에서 jsx문법으로 () 안에 html 요소를 리턴하거나 {} 통해 js 코드를 넣을수 있던것과 비슷한 구조 입니다.

```java
<!
<html lang=kr>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="/static/css/style.css">
</head>
<body>
<div class="card" th:each="eatitle :${title}">
    <h4 th:text="${eatitle ? 'true': 'false'}"></h4>
    <div th:if="${eatitleStat}">
        <p>eatitle이 ture 라면 표시될 내용</p>>
    </div>

    <h4 th:text="${eatitle}">dddd</h4>
</div>

</body>
</html>
```

```java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 내부 상세 페이지를 담당
@Controller
@RequestMapping(value = "/Details") // 이렇게하면 기본값이 되는 엔드 포인트가 됨
@RequiredArgsConstructor
// Getmaaping이전의 Method 를 정의해야 하는 이전의 방법이지만 Method를 정의하지 않으면 내부에서
// Getmapping , DeleteMapping 등 모두 다 사용가능
public class DetailController {
    private final UserRepository userRepository;

//    @Autowired
//    public DetailController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    } // @RequiredArgsConstructor 의 역할이에요 인자가 필요한 생성자를 정의해줍니다.
// lombok의 어노테이션 메서드 사용하기 싫으면 이렇게하면됩니다.

    @GetMapping("/detailsd")
    public String detailsd(Model model) {
        return "index.html";
    }
    @GetMapping("/detailsa")
    public String detailsa(Model model) {
        // User 테이블 꺼내주세요

        String DBdata =  "서버에서 데이터에요";
        model.addAttribute("title", DBdata);
        // 첫번째 인자 변수 , 두번째 인자 넣을 데이터
        return "home.html";
    }
    @GetMapping("/detailsb")
    public String detailsb(Model model) {
        // User 테이블 꺼내주세요
        User userProxy=userRepository.getReferenceById(1); // ID 값이 1인 유저를 가져옵니다.
        Integer age= userProxy.getAge();
        String name = userProxy.getName();
        model.addAttribute("title", age+name);
        // 첫번째 인자 변수 , 두번째 인자 넣을 데이터
        return "home.html";
    }
    @GetMapping("/GetAll")
    public String GetAll(Model model) {
        List<User> userProxy = userRepository.findAll();
        List<String> nameList = new ArrayList<>();
        List<Integer> ageList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();

        for (User user: userProxy ){
            nameList.add(user.getName());
            ageList.add((user.getAge()));
        }
        if (nameList.size() == ageList.size()){
            for (int i = 0 ; i<nameList.size(); i++){
                map.put(nameList.get(i),ageList.get(i));
            }
        }
        model.addAttribute("title", map);
        return "index.html";
    }

}

```

이렇게 map 에 있는 데이터를  title이라는 속성으로  해당 html에 넣을수 있습니다.

속성으로 넣은 데이터는 모델이 아래에서 return 하는 HTML 컨트롤러에서 모델에 데이터를 추가한 후 해당 데이터를 HTML 템플릿으로 전달하여 뷰를 생성하는 과정을 통해 모델의 데이터를 HTML에 적용시킬 수 있습니다

# 서버를 통해 데이터 베이스에 데이터를 넣으려면

1. post 요청을 할 html 
2. 서버에서의 데이터 DB insert 요청 후 HTML 리다이랙션 
3. 리다이랙션을 할 html 

> Post 요청을 할 HTML
> 

```java
!
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form action="/add" method="post">
    <input name="name">
    <input  name="age">
    <button>전송 </button>>
</form>>

</body>
</html>
```

> 서버에서의 DB INSERT 요청 후 HTML 리다이랙션
> 

```java
   @PostMapping("/user")
    public String submit(@RequestParam  String name,
                         @RequestParam  Integer age){
        User user = new User(name,age);

        if(userRepository.save(user)!=null){
            return  "redirect/home";
        }
        return  null;

    }// 이렇게해도 되고
     @PostMapping("/user")
    public String submit(@ModelAttribute User user){
 

        if(userRepository.save(user)!=null){ // 저장 후 리다이랙션
            return  "redirect/list";
        }
        return  null;

    }
```

> 리다이랙션할 HTML
> 

```java

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div th:replace="nav::navbar"><!-- ~ 상대경로 ~-->

</div>
<div th:each="eaUser : ${User}">
    <h4 th:text="${eaUser.name}"> </h4>
    <h4 th:text="${eaUser.age}"> </h4>
</div>

</body>
</html>
```

> 저장된 데이터를 보기위한 List .HTML
> 

```java
 @GetMapping("/list")
    public String GetAll2(Model model) {
        List<User> userProxy = userRepository.findAll();
        List<String> nameList = new ArrayList<>();
        List<Integer> ageList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        List<User> userList = new ArrayList<>();

        for (User user: userProxy ){
            nameList.add(user.getName());
            ageList.add((user.getAge()));
        }
        for (int i = 0; i < nameList.size(); i++){
            userList.add(new User(nameList.get(i),ageList.get(i)));
        }
        model.addAttribute("User", userList);

        return "list.html";
    }
```

# UserRepository에 Optional 적용시키기

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2016.png)

detail 웹  페이지는 위와 같이 동작합니다. 이렇게 구현하기 위해서는 아래와 같은 순서가 필요합니다. URL 파라미터 

1. @GetMapping detail 엔드포인트에서 유저에게서 받은 id 값 
2. UserRepository를 통한 findById 메서드 또는 getRefernceById 를 사용하여 데이터 가져온 후 HTML 반환
3. 서버에서 addAttribute 된 HTML 을 통해 접속 

`Optional` 을 사용하여 값이 있을 수도 없을 수도 있는 상황에서 사용됩니다. 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2017.png)

> detail 엔드포인트에서 클라이언트에서 id 값 받아오기
> 

```java
 @GetMapping("/mydetail/{id}")
    public String getDetail(@PathVariable Integer id , Model model){
        User user =userRepository.getReferenceById(id);
        // id 에 해당하는 엔티티 인데요
        Optional<User> optUser = userRepository.findById(id);
        // id 에 해당하는 Optional로 래핑된 엔티티 인데요

        // 차이점 :
        // getReferenceById의 경우
        // id에 해당하는 엔티티 반환 , 엔티티 없으면 에러 발생

        // findById의 경우: 해당 하는 Optional 로 래핑하여
        // entity 또는 없는 경우 Optional.empty를 반환한다.
        // 내부적으로 예외를 발생시키지 않음
        model.addAttribute("User",optUser.orElse(null));
            // 또는
//        optUser.ifPresent(idxUser->model.addAttribute("User",idxUser));
        // 이렇게 사용가능

        return "detail.html";
    }
```

> 반환할 HTML
> 

```java

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div>
    <p th:text="${User.name}+ '입니다.'"></p>
    <p th:text="${User.age}+ '입니다.'"></p>

</div>
</body>
</html>
<!--상세 페이지-->
```

> getReferenceById VS findByID
> 

# findById()

![](https://velog.velcdn.com/images/bbbbooo/post/a73c94fc-8434-4775-8ec0-091ed5b2a836/image.png)

`findById()`는 `CrudReposiotry`에 정의된 메소드다.

해당 메소드는 **전달받은 Id에 해당하는 엔티티** 혹은 **해당하는 엔티티가 없을 경우 Optional.empty()를 반환**한다고 한다. 즉, 엔티티가 없더라도 내부적으로 예외를 발생시키지 않는다.

물론, Id가 유효하지 않다면 `IlleagalArgumentException`이 발생할 수 있다. 이는 Throws에서 확인할 수 있다.

그렇다면 `getReferenceById()`는 어떨까?

# getReferenceById()

![](https://velog.velcdn.com/images/bbbbooo/post/040fb00f-5a2c-4110-a930-70ff2640268b/image.png)

`getReferenceById()`는 `JpaRepository`에 정의된 메소드다.

해당 메소드는 **전달받은 Id에 해당하는 엔티티를 반환**한다. **없을 경우 내부적으로 예외를 발생**시킨다.

## 차이점

현재까지 알게된 것을 정리하면 다음과 같다.

**findById()**

- id에 해당하는 엔티티 반환, 엔티티 없으면 Optional.empty() 반환
- 내부적으로 예외가 발생하지 않음

**getReferenceById()**

- id에 해당하는 엔티티 반환, 엔티티 없으면 예외 발생

`getReferenceById()` 는 EntityManager의 getReference 메서드를 호출하여, 참조값만 가져온 후, 조회된 엔티티의 내부 값이 필요해지는 시점에 **지연 로딩으로 DB를 조회해 값을 가져오도록 동작**한다. 따라서, **값을 단순하게 할당하는 기능에서는 `findById()`에 비해 성능상의 이점**이 있다.

> 프록시
> 

로딩 전략을 알기 전, `지연로딩`의 핵심인 프록시에 대한 개념부터 알아보도록 하자.

객체는 객체 그래프로 연관된 객체들을 자유롭게 탐색할 수 있다. 하지만 데이터베이스에 매핑하는 엔티티 객체에서는 자유도가 떨어진다. 연관된 테이블의 데이터를 조회하기 위해 Join을 사용하여 조회해야 하기 때문이다.

자유로운 객체 그래프 탐색의 가능성으로 인해 연관된 모든 테이블을 조회하는 것은 비용이 따른다. 실제로 연관된 테이블을 사용하지 않으면 쓸데없이 Join으로 조회한 결과를 가져오기 떄문이다.

이러한 문제를 해결하기 위해 프록시가 등장하였다.

연관된 객체를 처음부터 모두 조회하는 것이 아니라, 실제 사용 시점에 조회할 수 있도록 해준다.

**JPA에서 프록시는 실제 엔티티 객체 대신 DB 조회를 지연할 수 있는 가짜 객체**를 의미한다.

실제 엔티티 클래스를 상속받아 만들어지므로 사용자 입장에서는 진짜 객체인지 가짜 객체인지 구분하지 않고 사용하면 된다.

겉모양이 같아 사용자 입장에서 동일하게 사용하면 내부적으로는 다르게 동작한다.

프록시 객체의 메서드를 호출하면 참조를 통해 메서드 호출을 위임하고 실제 객체의 메서드를 호출한다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2018.png)

> 
> 
> 1. `getReference()`를 통해 프록시 객체를 생성한다. 해당 프록시 객체의 정보가 영속성 컨텍스트에 1차 캐시로 존재한다면 프록시 객체가 아닌 실제 객체를 반환한다. 아직 사용이 존재하지 않기에 실제 엔티티 객체의 참조를 가지고 있지 않는다.
> 2. 실제 사용을 위해 메소드를 호출한다.
> 3. 메서드가 호출되면 영속성 컨텍스트에 초기화를 요청한다. 실제 엔티티 객체의 참조를 가지기 위해서는 실제 엔티티 객체의 생성이 필요한다.
> 4. 영속성 컨텍스트는 데이터베이스를 조회해서 실제 엔티티 객체의 정보를 얻는다.
> 5. 조회된 정보를 바탕으로 실제 엔티티 객체를 생성한다.
> 6. 참조를 통해 실제 엔티티 객체의 메서드를 호출한다.
> 7. 메서드 호출의 결과를 반환한다.

## 즉시로딩, 지연로딩

프록시 객체를 사용하게 되면 자주 함께 사용하는 것은 바로 가져올 수도 있고, 사용할 때 가져올 수 있도록 설정하는 방법이 존재한다. 이름에서 알 수 있듯 바로 가져오는 방법이 즉시 로딩이고 사용할 때 가져오는 방법이 지연 로딩이다. 각각의 특징은 무엇일까? 결론부터 말하면 다음과 같다.

> ✅ 즉시로딩과 지연로딩의 차이
> 
> 
> **EAGER(즉시로딩)는** 사전적 의미인 열심인, 열렬한 처럼 Member를 조회하면 연관관계에 있는 Team 역시 함께 조회한다. **즉, 한번에 모두 가져온다!**
> 
> **LAZY(지연로딩)는** 게을러서 Member만 조회해오고 연관관계에 있는 나머지 데이터는 실제로 사용할때까지 조회를 미룬다. **즉, 사용할 때 호출이 된다!**
> 

### 즉시 로딩

```java
@Entity
public class Member {
    // ...
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    // ...
}

```

![](https://velog.velcdn.com/images/bbbbooo/post/28a437ed-9f5e-4283-8653-f0b9228da4fb/image.png)

**엔티티를 조회할 때 연관관계에 있는 엔티티도 함께 조회하는 방법**이다. 즉시 로딩을 사용하기 위해서는 fetch 속성을 `FetchType.EAGER`로 지정한다. JPA 구현체는 즉시 로딩을 최적화하기 위해 가능하면 조인 쿼리를 사용한다.

즉시 로딩 실행 쿼리문을 보면 JPA가 내부 조인(INNER JOIN)이 아닌 외부 조인(LEFT OUTER JOIN)을 사용하는 것을 확인할 수 있는데 이는 `NULL 가능성` 때문이다.

현재 회원 테이블에서 `team_id` 외래 키는 NULL 값을 허용하고 있다. 내부 조인이 외부 조인보다 성능이 좋기에 최적화를 위해 내부 조인을 사용하는 것이 유리한데 이때는 외래키에 NOT NULL 제약 조건을 설정하면 값이 있는 것을 보장하기 때문에 이런 경우에는 내부조인만 사용해도 된다.

```java
@Entity
public class Member {
    // ...
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;
    // ...
}
```

![](https://velog.velcdn.com/images/bbbbooo/post/3de93c69-1243-4a07-8f83-1da3f13409c5/image.png)

위와 같이 외래키에 NULL 값을 허용하지 않는다고 JPA에게 알려주는 경우 외부 조인 대신 내부 조인을 사용하게 된다.

> ✅ nullable 설정에 따른 조인 전략
> 
> 
> `@JoinColumn(nullable = true)`: NULL 허용(기본값), 외부 조인 사용
> 
> `@JoinColumn(nullable = false)`: NULL 허용하지 않음, 내부 조인 사용
> 

### 지연 로딩

```java
@Entity
public class Member {
    // ...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    // ...
}
```

**연관된 엔티티를 프록시 객체로 불러오고 실제 사용할 때 DB를 조회하는 방법**이다. 지연 로딩을 사용하기 위해서는 fetch 속성을 `FetchType.LAZY`로 지정한다. 지연 로딩을 사용하게 되는 경우 실제 엔티티 객체 대신 앞에서 설명한 프록시 객체가 들어가게 된다. 값이 실제로 사용될 때, DB에서 조회하기 때문에 DB의 부하를 줄여줘 즉시로딩보다 성능상의 이점이 있다.

위 코드에서는 `Member`를 호출하면 `Team`을 조회하진 않는다. 대신 team 멤버변수에 프록시 객체를 넣어 둔다. 이 프록시 객체는 실제 사용될 때까지 데이터 로딩을 미룬다.

> ✅ JPA 기본 Fetch 옵션
> 
> 
> `@ManyToOne`, `@OneToOne` : 즉시 로딩(FetchType.EAGER)
> 
> `@OneToMany`, `@ManyToMany` : 지연 로딩(FetchType.LAZY)
> 

**로딩 전략의 경우 되도록 지연 로딩만 사용하도록 권장**하고 있다. 그 이유는 즉시 로딩을 사용하게 되면 예상치 못한 문제가 발생할 가능성이 존재하기 때문이다. 예를 들면 N+1 문제처럼, select로 Member만 가져오는 SQL을 작성했지만, 해당 SQL의 개수만큼 필요하지도 않은 Team select SQL가 발생하게 될 수도 있다. 그렇다고 지연 로딩을 하면 무조건 N+1을 막는 것은 아니다.

`fetchJoin`, `@EntityGraph`, `fetchSize`등 다양한 해결방법이 존재하고, 각각의 프로덕션 상황에 따라서 적절히 방법을 고르는 것이 중요하다.

# @Service 레이어를통한 분할

**`@Controller 계층` : HTTP 요청과 응답을 처리하며, 클라이언트와 상호작용합니다. 주로 HTML 렌더링이나 데이터 통신을 담당합니다.**

**`@Service 계층` : 비즈니스 로직을 처리하며, 주로 Controller와 Repository 사이에서 중개자 역할을 합니다. 비즈니스 규칙을 구현하고, 여러 개의 Repository를 조합하여 트랜잭션을 관리합니다.**

**`Repository 계층`** : 데이터베이스와의 상호작용을 처리하며, 주로 CRUD(Create, Read, Update, Delete) 작업을 수행합니다. 데이터베이스에 액세스하는 데 필요한 쿼리를 실행하고, 엔티티를 영속화합니다. 

**`Entity 계층`** :  애플리케이션에서 사용되는 데이터 모델을 정의합니다. 일반적으로 데이터베이스의 테이블과 매핑되며, JPA(Entity Manager)를 통해 데이터베이스와 상호작용합니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2019.png)

> Entity 계층 : 데이터 모델  → JPARepositrory 와 상호작용
> 

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 테이블 생성하려면 @Entity로 생성
@Getter
@NoArgsConstructor// 기본 생성자 제공
@Entity // 독립체라고 함
@Table(name = "User")
public class User {//해당 이름으로 테이블 하나생성해줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId; // 컬럼용 변수에는 Ineger를 써야함
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    Integer age;

    @Builder
    public User(  String name, Integer age) {

        this.name = name;
        this.age = age;
    }

//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    } @Getter로 쓸필요 X
}

```

> Repository 계층  테이블 엔티티와 상호작용→ @Service 계층에서의 DI 객체
> 

```java
package com.example.demo.repository;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                            //< Entity 명 ,id 컬럼타입>
public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByName(String username);
    User findByAge(int age);
}

```

> Service 계층 DI로 주입된 Repository 계층을 사용 로직을 담당 
EX )  예외처리 , DB 연산 수행
> 

```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //@RequiredArgsConstructor 사용하면
//    @Autowired
//    UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    } // 사용안해도됨

    public boolean saveUser(String name , Integer age) {
        try {
            if(name!=null&&age!=null) {
                User user = new User(name, age);
                User userSucceed= userRepository.save(user);
                return  true;
            }

         return false;
        }
        catch (Exception e) {
            System.out.println(e.getCause());
            return false;
        }

    }
}

```

> Controller 계층 DI로 주입된 서비스 계층을 사용
> 

```java
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyUserController {
    private final UserService userService;

    @PostMapping("/insert")
    public String insert(@ModelAttribute User user, Model model) {
        boolean isSucceed= userService.saveUser(user.getName(),user.getAge());
        if (isSucceed){
            model.addAttribute("user",user);
            return "redirect:/my/list";
        }
        return "redirect:/error";
    }
}
	 
```

이런식으로   `Entity` 에서 테이블을 데이터 클래스를 정의하고  그걸 통한 `JPARepository`를 상속받은  인터페이스를  정의하여 `JpaRepository`를 상속받는 구현체는 이러한 메서드를 상속받아 기능을 사용할 수 있습니다. 

이후 DI 객체로서  `@Service` 계층에서는 `@Repository` 계층을 DI객체로서 주입받고 `@Controller`계층은 @Service 계층을 DI로서 주입받습니다. 

> JPARepository를 통한 UPDATE 구현
> 

`JPA`에서 `UPdate`는 따로 제공하지 않고 Save 메서드를 활용하여 구현할 수 있습니다. 

> Service 계층
> 

```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //@RequiredArgsConstructor 사용하면
//    @Autowired
//    UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    } // 사용안해도됨

    public boolean saveUser(String name , Integer age) {
        try {
            if(name!=null&&age!=null) {
                User user = new User(name, age);
                User userSucceed= userRepository.save(user);
                return  true;
            }

         return false;
        }
        catch (Exception e) {
            System.out.println(e.getCause());
            return false;
        }

    }
    public boolean updateUserAgeByName(String targetName , Integer updateAge) {
        try {
            if (targetName != null && updateAge != null) {
                User user = userRepository.findByName(targetName);
                user.setAge(1);
                User savedUser = userRepository.save(user);
                return savedUser != null;
            }
            else {

                throw new IllegalArgumentException("인자 예외 발생");

            }

        }
        catch (IllegalArgumentException e){
            return false;

        }
    }
}
 
```

> Controller 계층
> 

```java
 @PostMapping("/save")
    public String save(@ModelAttribute User user, Model model) {
        boolean isSucceed= userService.updateUserAgeByName(user.getName(), user.getAge());
        boolean apiServer = true;
        if (isSucceed){
            model.addAttribute("user",user);
            return "redirect:/Details/list"; // 웹서버의 경우
        }
        else return "redirect:/error";
    }

    //api 서버의 경우
    @PostMapping("/save1")
    public ResponseEntity<String> save1(@ModelAttribute User user, Model model) {
        boolean isSucceed= userService.updateUserAgeByName(user.getName(), user.getAge());
        boolean apiServer = true;
        if (isSucceed){
            model.addAttribute("user",user);
            return  ResponseEntity.ok("ok");
        }
        else return  ResponseEntity.ok("error");
    }
```

```java
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form action="/add" method="post">
    <p name="id" th:value="${data.id}"></p>
    <input name="name" th:value="${data.name}">
    <input name="age" th:value="${data.age}">
    <button>전송</button>
</form>
</body>
</html>

```

> Ajax 요청을 통한 Delete 요청 
- Put / Delete 요청은 Ajax 로 보통 요청합니다.
> 

Put 요청이나 Delete 요청은 form 태그에서 지원하지  않습니다.

> Controller 계층 ( Body 속성 방식 )
> 

```java
 @PostMapping("/manage")
    public ResponseEntity<String> delete(@RequestBody Map<String ,Integer> map) {
        Integer userId=map.get("userId");
        boolean isSucceed= userService.deleteUserById(userId);
        if (isSucceed){
            return ResponseEntity.status(200).body("저장 성공");
        }
        return ResponseEntity.status(500).body("error");

    }
    @GetMapping("/list")
    public String GetAll2(Model model) {
        List<User> userProxy = userService.findAll();
        model.addAttribute("User", userProxy);

        return "list.html";
    }
```

> Service 계층
> 

```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    //@RequiredArgsConstructor 사용하면
//    @Autowired
//    UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    } // 사용안해도됨

    public boolean saveUser(String name , Integer age) {
        try {
            if(name!=null&&age!=null) {
                User user = new User(name, age);
                User userSucceed= userRepository.save(user);
                return  true;
            }

         return false;
        }
        catch (Exception e) {
            System.out.println(e.getCause());
            return false;
        }

    }
    public boolean updateUserAgeByName(String targetName , Integer updateAge) {
        try {
            if (targetName != null && updateAge != null) {
                User user = userRepository.findByName(targetName);
                user.setAge(1);
                User savedUser = userRepository.save(user);
                return savedUser != null;
            }
            else {
                throw new IllegalArgumentException("인자 예외 발생");
            }
        }
        catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean deleteUserById(Integer id) {
        try{
            User user= userRepository.findById(id).orElse(null);
            if(user!=null) {
                userRepository.delete(user);
                if (userRepository.findById(id).isEmpty()){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> findAll(){
        List<User> users= userRepository.findAll();
        return users;
    }

}

```

> HTML ( Body 속성   방식 )
> 

```java

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div th:replace="nav::navbar"><!-- ~ 상대경로 ~-->

</div>
<div th:each="eaUser : ${User}">
    <a th:href ="@{Details/mydetail/eaUser.userId}" th:text="${eaUser.userId}"> </a>
    <h4 th:text="${eaUser.name}"> </h4>
    <h4 th:text="${eaUser.age}"> </h4>c
    <button onclick="handleClick('${eaUser.userId}')">삭제버튼 </button>

</div>
<button class="btn">bt </button>

<script>
  function handleClick(userId){
      fetch('http://localhost:8080/my/manage',{
              method: 'POST'// POST 요청으로 변경
            ,
          headers: {'Content-Type':'application/json'}
            ,
          body: JSON.stringify({userId: 2})
        });
    }

</script>

</body>
</html>
```

# Seession , JWT ,  OAuth 개념

> Session 방식
> 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2020.png)

`Session` 방식의 경우 인증 된 유저에게 `Session ID`를  발급해주고 유저의 `Get/Post` 요청 마다  Session ID를 DB에서 확인하여 요청들을 유효성을 확인해주는 매개체입니다.

> JWT (JSON WEB TOKEN) 방식
> 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2021.png)

`JWT(JSON Web Token)` 방식의 경우 , `Header , Payload ,Signature` 으로 되어 있고 유저가 로그인 시 해당 JW 토큰을 발급해줍니다.  Session 방식과 달리 DB에 저장하는 방식이 아닌 유저가 Get/Post 요청 마다  해당 JW 토큰을 제공하는 방식입니다.

서버는 이 `JW 토큰`을 검증하기 위해 서명을 확인하고, 페이로드에 포함된 클레임을 해독하여 사용자의 인증 및 권한을 확인합니다. 이러한 방식으로 세션과 달리 서버에 사용자의 상태를 저장하지 않고도 사용자를 인증할 수 있습니다.

> OAuth 방식
> 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2022.png)

`OAuth(Open Authorization)`는 사용자가 웹 또는 애플리케이션에서 다른 웹 서비스에 대한 제한된 액세스를 위임할 수 있도록 하는 인증 및 권한 부여 프로토콜입니다. 

웹 사이트에서  흔히 보이는 `소셜로그인`과 같습니다. (A 사이트에서 B사이트 계정을 통해 가입하거나 로그인할 수 있는 기능)

`소셜로그인`  :  OAuth를 사용하면 사용자가 자사 애플리케이션에 직접 가입하지 않고, 대표적인 소셜 미디어 플랫폼(예: Google, Facebook, Twitter 등)의 계정을 사용하여 로그인할 수 있습니다.

**`API 액세스`**: OAuth를 사용하여 다른 애플리케이션의 API에 대한 액세스 권한을 부여할 수 있습니다. 예를 들어, 사용자가 자사 애플리케이션에서 Google 계정을 사용하여 로그인하면, 해당 애플리케이션은 Google API에 액세스할 수 있는 권한을 얻을 수 있습니다.

 

> Spring Security 설치
> 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2023.png)

```java
implementation 'org.springframework.boot:spring-boot-starter-security' //  Spring Security
	implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE'
```

이렇게 하면 모든 페이지에서 로그인에 대한 페이지로 블로킹  되어있을 겁니다.

프로젝트 내에 config 패키지 만든 후 java 파일 하나만든다음에 아래와 같이 작성해주셔야 합니다.

```java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 이 두개 어노테이션을 통해 Spring Security 웹 보안을 구성할 수 있습니다.
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((authorize)->
                authorize.requestMatchers("/**").permitAll()
                );
        return http.build();
    }
}

```

# BCryptPasswordEncoder

`BCryptPasswordEncode` 는  해시 함수로서 비밀번호를 암호화 해주는 클래스입니다.

이걸 Controller 로 에서 DI로 객체로서 주입하는 방법은 아래와 같습니다.

`@Configuration` 과 `@Bean`을 사용하면 메서드를 빈으로 정의하면  Spring 컨테이너는 이 메서드를 호출하여 반환되는 객체를 빈으로 등록합니다.

```java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 이 두개 어노테이션을 통해 Spring Security 웹 보안을 구성할 수 있습니다.
public class SecurityConfig {
    @Bean  
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((authorize)->
                authorize.requestMatchers("/**").permitAll()
                );
        return http.build();
    }
}

```

> Controller 계층
> 

```java
package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
@Configuration
@Controller
@RequiredArgsConstructor

public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/member")
    public String index() {
        return "login.html";
    }

    @PostMapping("/member/join")
    public ResponseEntity<String> join(@RequestBody Member member) {

        String encodedPassword=  bCryptPasswordEncoder.encode(member.getPassWord());
        boolean isSucceed=memberService.save(member.getUserName(), encodedPassword, member.getDisplayName());
        if (isSucceed){
            return ResponseEntity.status(200).body("succeed");
        }

        return ResponseEntity.status(500).body("error");
    }
    @PostMapping("/member/login")
    public ResponseEntity<String> login(@RequestBody Member member, HttpServletResponse response) throws IOException {

        Member targetMember=memberService.login2(member.getUserName() );
        boolean is_matched =new BCryptPasswordEncoder().matches(member.getPassWord(),targetMember.getPassWord());
        if (is_matched){

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION,"/member/list").build();
            // Post 요청에서 바로 리다이랙션을 해주는것이 아닌  Get 요청을 한번더 요청해서 리다이랙션해야합니다.
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
    }
    @GetMapping("/member/list")
    public String list() {
        return "redirect:/my/list";
    }
}

```

> userAuth.html
> 

```java

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div>
    <input id="id" name="id">
    <input id="password" name="password" type="password">
    <input id="displayname" name="displayname" >

    <button onclick="joinHandler()" >join</button>
    <button onclick="loginHandler()">login</button>

</div>

<script src="/js/userAuth.js"></script>

</body>
</html>
<!--상세 페이지-->
```

> userAuth.js
> 

```java
function loginHandler(){
    var Id= document.getElementById("id").value;
    var Password= document.getElementById("password").value;
    var displayName = document.getElementById("displayname").value;

    var Member ={
        userName : Id,
        passWord : Password,
        displayName : displayName

    };
    fetch('/member/login',
        {
            method: 'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(Member)

        })
        .then(res=>{
            if (res.ok){
                alert("Login Succeed");
                if (res.redirected){
                    window.location.href=res.url;
                }
            }
            else if (!res.ok){
                throw new Error("failed");
            }
            return res.text()
        })
        .catch(error=>{
            alert(error)
        });

}
function joinHandler(){
    var Id= document.getElementById("id").value;
    var Password= document.getElementById("password").value;
    var displayName = document.getElementById("displayname").value;

    var Member ={
        userName : Id,
        passWord : Password,
        displayName : displayName

    };
    fetch('/member/join',
        {
            method: 'POST',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(Member)

        })
        .then(res=>{
            if(res.redirected){
                location.href(res.url);
            }
            else if (!res.ok){
                throw new Error("failed");
            }
            return res.text()
        })
        .then(data=>{
            alert(data);
        })
        .catch(error=>{
            alert(error)
        }).then(()=>{
        location.reload();
    })

}
```

반환 타입만으로 빈을 주입하는 것은 스프링의 의존성 주입(Dependency Injection) 원칙 중 하나입니다. 스프링은 컨테이너에 등록된 빈을 검색할 때 반환 타입을 기반으로 합니다.

- `라이브러리 클래스  DI객체로 주입받는법`
1. 클래스에서 @Configuration 어노테이션 정의후 함수를 통해 해당 클래스 반환하도록 하고 해당 함수에서 @Bean 등록
2. 해당 DI 객체를 사용하는 곳에서 반환 타입 등록하면 끝입니다. 함수명이랑 상관없습니다.

 

**`@Bean`** 어노테이션은 해당 메서드가 스프링 애플리케이션 컨텍스트에 빈으로 등록될 것임을 나타냅니다. 그리고 **`@Configuration`** 어노테이션은 스프링이 해당 클래스를 설정 클래스로 인식하고 빈을 정의할 수 있는 클래스임을 나타냅니다.

이런 구성으로  로그인을 구현할 수 있습니다.

그런데 , 이제 `Spring Security` 에서는 더욱 간단하게 구현하는것을 제공해줍니다. 
(Spring-Security 의 경우 form 기반의 로그인을 지원하기에 fetch를 사용하지 않고 구현하겠습니다. )

> SecurityConfig  재구성
> 

```java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 이 두개 어노테이션을 통해 Spring Security 웹 보안을 구성할 수 있습니다.
public class SecurityConfig {
    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((authorize)->
                authorize.requestMatchers("/**").permitAll()
                );
        http.formLogin((formlogin)->formlogin.loginPage("/login").defaultSuccessUrl("/"));
        return http.build();
    }
}

```

이런식으로 구성하는 방법을 통해 로그인시 제공할 페이지와 또는 로그인 실패도 추가할 수 있습니다. 위와 같이 정의하지 않으면 실패시 기본적으로 /login?error 로 이동합니다.

> Controller 재구성
> 

```java
package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
@Configuration
@Controller
@RequiredArgsConstructor

public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/member")
    public String index() {
        return "login.html";
    }

    @PostMapping("/member/join")
    public ResponseEntity<String> join(@RequestBody Member member) {

        String encodedPassword=  bCryptPasswordEncoder.encode(member.getPassWord());
        boolean isSucceed=memberService.save(member.getUserName(), encodedPassword, member.getDisplayName());
        if (isSucceed){
            return ResponseEntity.status(200).body("succeed");
        }

        return ResponseEntity.status(500).body("error");
    }
    @PostMapping("/member/login")
    public ResponseEntity<String> login(@RequestBody Member member, HttpServletResponse response) throws IOException {

        Member targetMember=memberService.login2(member.getUserName() );
        boolean is_matched =new BCryptPasswordEncoder().matches(member.getPassWord(),targetMember.getPassWord());
        if (is_matched){

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION,"/member/list").build();
            // Post 요청에서 바로 리다이랙션을 해주는것이 아닌  Get 요청을 한번더 요청해서 리다이랙션해야합니다.
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
    }
    @GetMapping("/member/list")
    public String list() {
        return "redirect:/my/list";
    }
    @GetMapping("/login")
    public String login() {
        return "login2.html";
    }
}

```

여기서 이제 위에서 Config 를 통해 정의한것처럼 기본 loginPage 설정을 통해 login 페이지를 정의하고 해당 설정을 정의할 수 있습니다.

> Login.html 재구성
> 

```java
!
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div th:if="${param.error}">
    <h4> 로그인 정보 불일치</h4>
</div>

<form action="/login" method="post"  >
    <input name="username">
    <input  name="password" type="password">
    <button>전송 </button>>
</form>>

</body>
</html>
```

`<div th:if="${param/error}">` 타임리프를 통해  Thymeleaf의 **`${param.error}`**는 URL의 쿼리 매개변수 중에서 **`error`**가 있는지를 확인하는 것입니다. 따라서 해당 조건이 참이면 해당 HTML 코드 블록이 렌더링됩니다. 

> MyUserDetailsService 정의
> 

```java
 package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

```

**`UserDetailsService`**는 Spring Security에서 사용자 정보를 로드하는 인터페이스입니다. 주로 사용자 인증 과정에서 사용됩니다. 이 인터페이스를 구현하여 사용자의 인증 및 권한 부여에 필요한 정보를 제공할 수 있습니다.

# Spring Security 로그인 동작 흐름

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2024.png)

1. **UserNamePasswordAuthenticationToken 생성**: 사용자가 로그인 폼에 사용자 이름과 비밀번호를 입력하면, 이 정보는 **`UsernamePasswordAuthenticationToken`** 객체로 생성됩니다. 이 토큰은 인증 매니저에 전달됩니다.
2. **AuthenticationManager 호출**: 생성된 **`UsernamePasswordAuthenticationToken`**은 **`AuthenticationManager`**에 전달됩니다. **`AuthenticationManager`**는 인증을 수행하는 주요 인터페이스이며, 실제 인증 프로세스를 처리합니다.
3. **Authentication Provider 검증**: **`AuthenticationManager`**는 등록된 **`AuthenticationProvider`** 목록을 반복하여 사용자의 자격 증명을 검증합니다. 각 **`AuthenticationProvider`**는 특정 인증 유형(예: 폼 기반 인증, LDAP 인증, OAuth 인증 등)을 처리합니다.
4. **UserDetailsService 호출**: 인증 프로세스의 중요한 부분 중 하나는 사용자 정보를 검색하는 것입니다. 인증 과정에서 사용자가 제공한 사용자 이름(또는 이메일 등)을 기반으로 실제 사용자 정보를 검색하기 위해 **`UserDetailsService`**가 호출됩니다.
5. **비밀번호 인코딩**: 사용자가 제공한 비밀번호와 저장된 사용자의 비밀번호를 비교하기 전에, 저장된 비밀번호를 인코딩하여 비교합니다. 이를 위해 주로 **`PasswordEncoder`**가 사용됩니다.
6. **인증 완료**: 사용자가 제공한 자격 증명이 유효하고, 사용자 정보를 성공적으로 검색했으며, 비밀번호가 일치하는 경우, 사용자는 인증된 것으로 간주됩니다. 이 상태는 **`Authentication`** 객체를 통해 표현되며, 이 객체는 **`SecurityContextHolder`**에 저장됩니다.
7. **로그인 성공**: 로그인이 성공하면 사용자는 보통 원래 요청한 페이지로 리디렉션됩니다. 이 때, 사용자에게 적절한 권한이 부여됩니다.

> Spring Security는 유저가 제출한 Password 와 DB에서 조회한 PassWord값을 자동으로 비교해줍니다. 
Spring Security는 어느 테이블에 유저 정보가 모르기 때문에  직접 테이블을 Entity로 서 제공해야합니다.
> 

> DB 조회하는 MemberRepository 구현
> 

`Derived Query Methods`란 `Spring Data JPA`에서 제공하는 기능 중 하나입니다. 이 기능을 사용하면 레포지토리 인터페이스에 정의된 메서드 이름을 통해 쿼리 메서드를 자동으로 생성할 수 있습니다.

```java
package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 이런 기법을 Derived Query methods 라고 합니다.
// and , or , 특정 문자 포함 , 특정 숫자 이상 , 이하 검색 , 정렬 가능합니다.
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByuserName(String username);
    Member findByUserNameAndPassWord(String userName, String passWord);
    Optional<Member> findByUsername(String userName);
}

```

> Repository 계층을 DI 로 가지는 Service 계층`MyUserDetailsService`
> 

```java
package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

		DI 객체 등록 
    private final MemberRepository memberRepository;
    @Override
                                        // 인자에는 유저가 제출한 username이 들어있습니다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member =memberRepository.findByUsername(username);
        /*User*/ //해당 클래스는 Spring Security 제공되는 User 객체입니다.
        if (member.isEmpty()){
            throw new UsernameNotFoundException("일치하는 아이디 없습니다.");
        }
        Member resMember = member.get();
          // User 객체에서는 3개지 정보가 필요합니다 유저이름, 유저 비밀번호 , 권한
        // 권한 주입 방법
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(resMember.getUserName(),resMember.getPassWord(),grantedAuthorities);
    }
}
```

위와 같이 `UserDetailsService` 를 인터페이스 상속을 받습니다.  이후 `User` 객체를 생성해서 리턴을 하는데 `UserDetailsService` ,`User`   모두  Sprng Security 에서 제공되는 클래스입니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2025.png)

`사용 가능한 권한(authorities)`은 `Spring Security`에서 사용자에게 할당된 권한을 나타냅니다. 이 권한은 사용자가 시스템에서 수행할 수 있는 작업을 정의하고, 보안 및 접근 제어를 관리하는 데 사용됩니다. 예를 들어, 사용자가 특정 페이지에 접근할 수 있는지 여부 또는 특정 작업을 수행할 수 있는지 여부를 권한에 따라 결정할 수 있습니다.

Spring Security에서는 권한을 문자열 형태로 나타내며, 이러한 문자열은 보통 "ROLE_"로 시작합니다. 예를 들어, "`ROLE_USER", "ROLE_ADMIN"`과 같은 형태로 정의됩니다. 이러한 권한은 사용자에게 부여되어 있거나 권한 기반 접근 제어(authorization)에 사용될 수 있습니다.

이렇게 유저를 Load 하는 서비스 계층을 정의하였지만  Controller 에서는 해당 객체를 DI로서 주입하지 않습니다. → 즉 , `MyUserDetailsService`  는  DI로서  `MemberComtroller` 계층에서 주입되지 않습니다.

 `Controller`에서는 주로 사용자 요청을 처리하고, 인증 및 권한 관리와 같은 보안 관련 로직은 Service 계층으로 위임됩니다. 

**`loadUserByUsername`** 메서드는 Spring Security 내부에서 사용자의 인증 요청이 있을 때 Spring Security 내부에서  자동으로 호출됩니다. 이 메서드는 **`UserDetailsService`** 인터페이스를 구현한 클래스에서 구현되어야 합니다. 사용자가 로그인하면 Spring Security는 사용자가 제공한 `username`을 기반으로 **`loadUserByUsername`** 메서드를 호출하여 사용자 정보를 검색합니다.

 

> 로그인 한 유저만 리다이랙션 하고자 한다면
> 

```java
// MemberController 
//  - ---- 추가 - --------
   // 로그인한 유저만 볼수 있게 하고 싶다면
    @GetMapping("/my-page") // Authentication auth 로그인한 유저 정보가 담겨저 있습니다.
    public String myPage(Authentication auth) {
        if (auth.isAuthenticated()) {
            System.out.println(auth.getName());
            System.out.println(auth.getAuthorities());
            System.out.println(auth.getCredentials());
            return "mypage.html";
	        }.   
        //auth // 현재 로그인 정보를 출력가능
        return "login.html";
    }
```

> mypage.html
> 

```java
<html xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<!--    타임리프에서 유저 정보를 넣으려면 -->
<div th:text="${#authentication.principal}"></div>
<div th:text="${#authentication.principal.username}"></div>

<div sec:authorize="hasAuthority('ROLE_USER')">
    <h4>권한 : USER </h4>
    <!--권한이 있는 경우에만 보여줌-->

</div>
<div sec:authorize="isAuthenticated()">
    <h4>접속 중인 유저만 </h4>
</div>
<div sec:authorize="isAnonymous()">
    <h4>로그아웃한 계정만  </h4>
</div>
</body>
</html>

```

> Security User 객체 커스터마이징 하기
> 

`Controller` 계층에서  로그인하 유저의 `displayName`을 가져오고 싶은데 가져올 수 없습니다.  그 이유는  `UserDetailsService` 계층에서 조회한 `Member` 객체를 통해  Spring Security User 객체를 통해 리턴하기 때문입니다. 

User 객체는 다음과 같습니다. 

```java
public User(    
	  String username,
    String password,
    @NotNull  java. util. Collection<? extends org. springframework. security. core. GrantedAuthority> authorities )
```

즉,  `UserDetailsService`에 있는 `loadUserByUsername` 메서드에서 리턴한 `User` 객체가  `Controller` 에서 사용되는 `Authentication` 으로 할당 되기 때문입니다.

> MyUserDetailsService 재구성
> 

```java
package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
                     // 인자에는 유저가 제출한 username이 들어있습니다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByuserName(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> grantedAuthorites = new ArrayList<>();
        grantedAuthorites.add(new SimpleGrantedAuthority("ROLE_USER"));
        MemberUser user = new MemberUser(member.getUserName(),member.getPassWord(),grantedAuthorites);
        user.setDisplayName(member.getDisplayName());
        return user;

    }

}

@Getter
@Setter
class MemberUser extends User{
    public String displayName;
    public MemberUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}

```

아래와 같이 기존 사용하던 User 클래스를 상속받고 super를 통해 부모 클래스의 생성자를 호출하여 부모 클래스의 초기화를 그대로 수행합니다. 

> Controller 계층에서의 displayName 메서드  호출
> 

Service 계층에서 리턴하였떤 객체는 `Authentication auth.getPrincipal()` 를 통해 객체를 가져올 수는 있습니다.  하지만 `getPrincipal`  객체에서 displayName과 관련된  메서드를 가져올 수 없는데요. 그 이유는 해당 반환 타입이 `Object` 타입이기 때문입니다.

이 상황에서 사용할 수 있는 것은 클래스 형 변환인데요. 아래와 같이 명시적으로  형 변환을 할 수 있습니다.

실제로 `getPrincipal` 함수는 타입 캐스팅을  권장 하는 메서드이기 떄문에 사용해도 됩니다.

```java
   // 로그인한 유저만 볼수 있게 하고 싶다면
    @GetMapping("/my-page") // Authentication auth 로그인이 완료 된 유저 정보가 담겨저 있습니다.
    public String myPage(Authentication auth) {
        if (auth.isAuthenticated()) {
            MemberUser user =  (MemberUser)auth.getPrincipal();
            String displayname =  user.getdisplayName();
            System.out.println(displayname);
            return "mypage.html";
        }
        //auth // 현재 로그인 정보를 출력가능
        return "login.html";
    }
```

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2026.png)

# Object 변환 DTO

```java
 // 로그인시 데이터 베이스에서
    @GetMapping("DtoTest") // Authentication auth 로그인이 완료 된 유저 정보가 담겨저 있습니다.
    public ResponseEntity<?> DtoTest(@RequestParam   Authentication auth) {
        if (auth.isAuthenticated()) {
            UserModel user =  (UserModel)auth.getPrincipal();
            String displayName =  user.getDisplayName();
            String userName = user.getUsername();
            System.out.println(displayName+userName);
            //
            return ResponseEntity.status(HttpStatus.FOUND).body(user);
        }
        //auth // 현재 로그인 정보를 출력가능
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail");

    }
```

해당 코드는 UserModel 객체를 그대로 내보내기 때문에 내부에서 Password 해시 값을 얻어낼 수 있다. 특정한 데이터만 객체화 해서 주고싶다면 DTO라는 어떤 클래스를 지칭하는 것이 아닌 DTO는 데이터 전송 객체(Data Transfer Object)입니다.

> UserDTO
> 

```java
package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {
    private Integer id;
    private String userName;
    private String password;
    private String displayName;

    UserDTO(Integer id){
        this.id = id;
    }
    UserDTO(Integer id, String userName, String password, String displayName){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
    }
    public UserDTO(String userName){
        this.userName = userName;
    }

}

```

	

> Controller 계층 재구성
> 

```java
 @GetMapping("DtoTest") // Authentication auth 로그인이 완료 된 유저 정보가 담겨저 있습니다.
    public ResponseEntity<?> DtoTest(@RequestParam   Authentication auth) {
        if (auth.isAuthenticated()) {
            UserModel user =  (UserModel)auth.getPrincipal();
            String displayName =  user.getDisplayName();
            String userName = user.getUsername();
            System.out.println(displayName+userName);
            UserDTO userDto =  new UserDTO(userName);
            // 생성자 오버라이딩을 통해 
            return ResponseEntity.status(HttpStatus.FOUND).body(userDto);
        }
        //auth // 현재 로그인 정보를 출력가능
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail");

    }
```

이런식으로 생성자 오버라이딩을 통해 목적에 맞는 데이터 전송 객체를 보낼수 있습니다.

 

# Index : 테이블에 속하는 속성

> Index가 필요한 이유
> 

데이터 베이스의 경우  , 행이 많아질 수록 부하가 심해지고 입출력 속도를 느려지게 만듭니다. 

인덱스란 , 데이터 베이스 내의 특정 열에 대한 빠른 데이터 검색 및 정렬을 지원하는  구조입니다. 이를  binary search 를 적용하여 검색 속도를 높일 수 있습니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2027.png)

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2028.png)

인덱스는 특정 열(컬럼)을 복사하고 정렬하여 추가적인 데이터 구조를 생성하는 것입니다. 이렇게 생성된 인덱스를 사용하면 데이터베이스가 해당 열에서 이진 검색(binary search)을 자동으로 해줍니다.

> JPA  Entity 에서 Index 만드는 방법
> 

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 테이블 생성하려면 @Entity로 생성
@Getter
@Setter
@NoArgsConstructor// 기본 생성자 제공
@Entity // 독립체라고 함
@Table(name = "User",indexes =
          //index로 할 컬럼  ,  해당 인덱스의 이름 
@Index(columnList = "age",name = "age_Index"))
public class User {//해당 이름으로 테이블 하나생성해줌
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId; // 컬럼용 변수에는 Ineger를 써야함
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    Integer age;

    @Builder
    public User(  String name, Integer age) {

        this.name = name;
        this.age = age;
    }

//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    } @Getter로 쓸필요 X
}

```

위와 같이 만들 수 있습니다.

> SQL 쿼리를 직접 작성해서 실행하려면
> 

```java
package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 이런 형식은 Derived Query methods 라고 합니다.
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByuserName(String username);
    Member findByUserNameAndPassWord(String userName, String passWord);
//    Optional<Member> findByUsername(String userName);
    Page<Member> findPageBy( Pageable pageable);
    @Query(value = "select * from TABLE ( member )where id = ?1 ", nativeQuery = true)
    Member rawQuery(Integer id);
}

```

위와 같이 @Query 어노테이션을 사용하여 직접 쿼리문을 생성하여 실행할 수 있습니다.

이와 같은 @Query 을 사용해서 JPA에서 지원되지 않는 full text index 도 사용 할 수 있습니다.

> 제 1,2 정규화
> 

테이블의 경우 

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2029.png)

정규화는 데이터베이스 설계 과정에서 중복을 최소화하고 데이터 일관성을 유지하기 위해 데이터를 구조화하는 프로세스입니다.

만약에 위에 같이 구성되어있디면, 아래와 같이 데이터 조회를 할 수 있습니다.

```java
SELECT *
FROM Sales s
JOIN Member m ON s.displayname = m.displayname
WHERE u.userId = :userId;
```

정규화 된 테이블을 가져오려면  아래와 같이 가져올 수 있습니다.

```java
@Entity
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY) // default 는 FetchType.EAGER 입니다.
    @JoinColumn(name ="member_id") //(이 컬럼이름 작명)
    private Member member;
    @CreationTimestamp
    private LocalDateTime created;

}

```

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2030.png)

정규화 된 테이블을 같이 가져오려면 아래와 같이 `@Query` 어노테이션을 통해 직접 쿼리 문을 작성하여 실행할 수 있습니다.

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query("SELECT s FROM Sales s JOIN FETCH s.member WHERE s.itemName = :itemName")
    List<Sales> findByItemNameWithMember(String itemName);
}
 
```

이런식으로 사용하는 것이  JPQL 문법입니다. 이때 @Query 내부에서 nativeQuery 설정을 꺼주셔야 합니다.

**`@ManyToOne`** 관계에서는 매핑된 엔티티의 식별자(ID)만이 외래 키 컬럼에 저장됩니다. 따라서 **`Sales`** 엔티티의 **`member`** 필드는 **`Member`** 엔티티의 ID 값과 매핑되어 **`Sales`** 테이블에는 **`Member`** 테이블의 ID 값만 저장됩니다.

그렇기에 Sales 테이블에 행에는 member 테이블의 모든 컬럼이 아닌 Id 컬럼만을저장하며 join Fetch 에서 데이터를 가져올때도 Sales의 member 에 해당하는 주 키값을 통해 Member 테이블에서 조회합니다. 두개의 테이블은 저장되면 아래와 같습니다.

> `Sales 테이블`
> 

| **id** | **itemName** | **price** | **count** | **member_id** | **created** |
| --- | --- | --- | --- | --- | --- |
| 1 | Product1 | 50 | 2 | 1 | 2024-04-25 12:00:00 |
| 2 | Product2 | 30 | 1 | 2 | 2024-04-25 14:30:00 |
| 3 | Product3 | 20 | 3 | 1 | 2024-04-26 09:45:00 |

> `member 테이블`
> 

| **id** | username | password | dispalyname |
| --- | --- | --- | --- |
| 1 | 김 |  | 김 |
| 2 | 이 |  | 이 |
| 3 | 박 |  | 박 |

이런식으로  `Sales` 저장시  `new Member`를 추가로  저장하지만 `Sales`의 `member_id` 에는 id 값만 들어가 있는 것을 확인할 수 있습니다. 그 이유는 `JPA` 에서 `@ManyToOne` 에서 저장되는 필드는  해당 테이블의 행 `id` 속성을 가지는 값과 매핑 됩니다. 즉 ,  @ManyToOne 은 매핑된 `Entity` 객체의 id 속성을 자동으로 저장하고 해당 필드를 조회 할때 자동으로 id 속성 값을 조회합니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2031.png)

`Sales` 테이블을 출력할때마다 `Member` 테이블의  Id 속성값을 조회하여  해당하는 Member 필드를 가져옵니다. 그런데 이제  `Sales`에서 `Member` 필드에 대한 것이  필요없을때도 있을겁니다.

> @ManyToOne   FetchType
> 

```java
@Entity
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY) // default 는 FetchType.EAGER 입니다.
    @JoinColumn(name ="member_id") //(이 컬럼이름 작명)
    private Member member;
    @CreationTimestamp
    private LocalDateTime created;

}

```

`@ManyToOne` 사용 시 `FetchType`  을 설정이 가능합니다. 

`FetchType` 에는  `EAGER , LAZY` 2가지가 있습니다.

> FetchType.EAGER
> 

`FetchType.EAGER` 는 해당 다대일 객체를 사용하지 않더라도 조회합니다. 즉 , 연관된 엔티티를 즉시 메모리에 로드하도록 하는 `FetchType` 입니다. 이는 해당 엔티티를 사용할때 지연로딩으로 인한 추가적인 쿼리를 방지하기 위해 사용될 수 있습니다.

`FetchType.EAGER`를 사용하면 연관된 엔티티를 즉시 메모리에 로드하므로, 지연로딩으로 인한 추가적인 쿼리를 발생하지 않습니다. 이는 연관된 엔티티를 실제로 사용할 때마다 발생하는 지연로딩으로 인한 성능 저하를 방지할 수 있습니

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SalesRepository extends Repository<Sales, Long> {
    
    @Query("SELECT s FROM Sales s")
    List<Sales> findAllSales(); //member 엔티티 필요로 하지 않지만 
	    // ManyToOne 에서 FetchType 이  EAGER인 경우 사용하지 않더라도 메모리에 로드
}
```

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2032.png)

> FetchType.LAZY
> 

FetchType.LAZY의 경우  해당 다대일 객체를 사용하지 않는 경우 해당 하는 필드를  메모리에 로드하지 않습니다.

LAZY 로딩을 사용하면 연관된 엔티티는 실제로 사용되는 시점에서 로드됩니다. 예를 들어, Sales 엔티티에서 LAZY 로딩으로 설정된 Member 엔티티를 조회할 때, Sales 리스트를 가져온 후에 실제로 Member 객체를 사용하는 시점에서 해당 Member 엔티티가 데이터베이스에서 로드됩니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2033.png)

이런식으로 사용하지 않는 경우 실제로 해당 엔티티가  사용되는 시점에 로드뒤고  불필요한 쿼리를 생성하지 않습니다. 

> @OneToMany
> 

OneToMany 관계는 하나의 엔티티가 여러 개의 관련된 엔티티를 가질 수 있는 경우에 사용됩니다. 이러한 관계는 현실 세계의 다양한 상황을 모델링하는 데  사용됩니다. 아래는 Member Entity 구성입니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2034.png)

```java
 @Entity
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String displayname;

    @OneToOne(mappedBy= "member") //  주 키 컬럼명  
    private List<Sales> sales =  new ArrayList()<>;
		// 사용되는 테이블 명 
}
```

이런식으로 관계가 `One` 쪽인 엔티티에서 자신의 주 키값을 가지는 다른 테이블의 행을 `Many` 를 가져올 수 있습니다. 이렇게 하면 JPA 에서 자동으로  `One` 에 해당 하는 `Entity` 출력 시 해당 `member_id` 가 기록된 Sales 행도 모두 출력할 겁니다.

![Untitled](Spring%20Boot%20&%20Java%20Spring%2035e5235ca9374331bf0a7ff581565a15/Untitled%2035.png)

> @OneToOne
> 

`@OneToOne`은 `JPA(Java Persistence API)`에서 관계형 데이터베이스의 테이블 간의 `일대일(1:1)` 관계를 매핑하기 위한 어노테이션입니다. 

```jsx
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    // Getters and setters
}

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
}

```

이런식으로 한 엔티티가 다른 엔티티와 일대일 관계를 가질 때, 양쪽 모두 **`@OneToOne`**으로 매핑되어야 합니다.
