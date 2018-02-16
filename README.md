# jreturn
A wrapper to wrap single or multi return values for java.

## Usage

Firstly, you need to make the method signature to return a `JReturn.R` instance.

e.g.

```java
public R doSomething() {
  // your code here
  return JReturn.single(result);
}
```

After you get an instance of `JReturn.R`, you can unwrap the value you returned from the method by key or by type.

#### Single return value

- create instance of R

```java
private R doSomethingWithSingleReturnValue() {
  String result = "Single value to be returned";
  return JReturn.single(result);
}
```

- unwrap single value from instance of R

```java
final R funcResult = doSomethingWithSingleReturnValue();
// funcResult is always non-null
final String strValue = funcResult.unwrap(String.class); // Single value to be returned
```

#### Multi return value(different type)

- create instance of R

```java
private R doSomethingWithMultiReturnValues() {
  String strResult = "String value to be returned";
  Integer intResult = 4396; // Integer value to be returned
  return JReturn.multi(strResult, intResult);
}
```

- unwrap value from instance of R

```java
final R funcResult = doSomethingWithMultiReturnValues();
// funcResult is always non-null
final String strResult = funcResult.unwrap(String.class); // String value to be returned
final Integer intResult = funcResult.unwrap(Integer.class); // 4396
```

#### Multi return value(with key indexed)

- create instance of R

```java
private R doSomethingWithKeyIndexedMultiReturnValues() {
  String firstStr = "First string result";
  String secondStr = "Second string result";
  Integer intValue = 4396;
  return JReturn.withKey("first", firstStr)
    .put("second", sencondStr)
    .put("third", intValue)
    .toR();
}
```

- unwrap value by key from instance of R

```java
final R result = doSomethingWithKeyIndexedMultiReturnValues();
// funcResult is always non-null
final String firstResult = result.unwrap("first", String.class); // First string result
final String secondResult = result.unwrap("second", String.class); // Second string result
final Integer intResult = result.unwrap("third", Integer.class); // 4396
```