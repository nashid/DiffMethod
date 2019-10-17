# DiffMethod
Finding differences between arguments of methods

## Libraries
I used RepoDriller and JavaParser libraries to implement this tool.

## How to build?

1. **Clone the repository**

2. **Navigate to the project root directory**

3. **Build a jar file**

```
./gradlew fatjar
```

## How to use?

1. **Navigate to the lib folder**

```
/project folder/build/libs
```

2. **Run a jar file**

```
java -jar CodingTask-all-1.0.0.jar
```

**Note: Results will be produced next to the jar file**

## Want an easier way?

Download the jar file from the release tab and use it

## Result

I have tested this tool with 4 repositories:

1. **My test repo**

* URL: <https://github.com/EhsanMashhadi/test>
    
* Result: <https://github.com/EhsanMashhadi/DiffMethod/blob/master/result/test.csv>
    
2. **Gson**

* URL: <https://github.com/google/gson>
    
* Result: <https://github.com/EhsanMashhadi/DiffMethod/blob/master/result/gson.csv>

3. **Retrofit**

* URL: <https://github.com/square/retrofit>
    
* Result: <https://github.com/EhsanMashhadi/DiffMethod/blob/master/result/retrofit.csv>
    
4. **Streamex**

* URL: <https://github.com/amaembo/streamex>
    
* Result: <https://github.com/EhsanMashhadi/DiffMethod/blob/master/result/streamex.csv>
