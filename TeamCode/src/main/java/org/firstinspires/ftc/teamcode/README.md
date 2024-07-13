## Naming Conventions

We will be discussing naming conventions throughout the standards, so let's set the stage with a few
basics:

* Use full English descriptors that accurately describe the variable/field/class. For example, use
  names like **firstName**, **grandTotal**, or **CorporateCustomer**. Although names like **x1**, *
  *y1**, or **fn** are easy to type because they are short, they do not provide any indication of
  what they represent and result in code that is difficult to understand, maintain, and enhance.
* Use terminology applicable to the domain. If your users refer to their clients as customers, then
  use the term **Customer** for the class, not **Client**. Many developers will make the mistake of
  creating generic terms for concepts when perfectly good terms already exist in the
  industry/domain.
* Use mixed case to make names readable. You should use lower case letters in general, but
  capitalize the first letter of class names and interface names, as well as the first letter of any
  non-initial word.
* Use abbreviations sparingly, but if you do so then use them intelligently. This means you should
  maintain a list of standard short forms (abbreviations), you should choose them wisely, and you
  should use them consistently. For example, if you want to use a short form for the word **number
  **, then choose one of **nbr**, **no**, or **num**, document which one you chose (it does not
  really matter which one), and use only that one.
* Avoid names that are similar or differ only in case. For example, the variable names *
  *persistentObject** and **persistentObjects** should not be used together, nor should *
  *anSqlDatabase** and **anSQLDatabase**.
* Avoid leading or trailing underscores. Names with leading or trailing underscores are usually
  reserved for system purposes, and may not be used for any user-created names except for
  pre-processor defines. More importantly, underscores are annoying and difficult to type so try to
  avoid their use whenever possible.
* Do Not "Hide" Names: Name hiding refers to the practice of naming a local variable, argument, or
  fields the same (or similar) as that of another one of greater scope. For example, if you have a
  field called **firstName** do not create a local variable or parameter called **firstName**, or
  anything close to it like **firstNames** or **fistName**. This makes your code difficult to
  understand and prone to bugs because other developers, or you, will misread your code while they
  are modifying it and make difficult to detect errors.

### Package Naming

There are several rules associated with the naming of packages. In order, these rules are:

* Local package names begin with an identifier that is not all upper case. Local packages are ones
  that are used internally within your organization and that will not be distributed to other
  organizations. Examples of these package names include **demoapp.persistence** and **demoapp.ui**.
* Global package names begin with the reversed Internet domain name for your organization. A package
  that is to be distributed to multiple organizations should include the name of the originating
  organization's domain name, with the top-level domain type capitalized. For example, to distribute
  the previous packages, they would be named **com.kmstechnology.demoapp.persistence**, *
  *com.kmstechnology.demoapp.ui**

### Class Naming

The standard Java convention is to use a full English descriptor starting with the first letter
capitalized using mixed case for the rest of the name. Class names shall be in singular form.

```java
public class Customer {
    // ...
}

public class Employee {
    // ...
}

public class Order {
    // ...
}

public class OrderItem {
    // ...
}
```

### Interface Naming

The Interface shares the same naming with Class.

A note for method members in an interface: By default, they are public access modifiers, so don’t
repeat public keyword in their method signatures

```java
public interface UserService {
    // DON'T
    UserStatus getUserStatus();

    //DO
    UserStatus getUserStatus();
}
```

### Enumeration Naming

The Enumeration shares the same naming with Class. All enumeration items should be all words
capitalized.

```java
public enum RecordStatus {
    AWAITING_REVIEW,
    IN_PROCESS,
    IN_ERROR,
    PROCESSED,
    MIGRATED
}
```

### Operation Naming

Operations should be named using a full English description, using mixed case with the first letter
of any non-initial word capitalized. It is also common practice for the first word of an operation
name to be a strong, active verb.

```java
public void execute(Command command){
        // ...
        }

public void exportUsers(List<User> users,OutputStream output){
        // ...
        }

public boolean authenticate(String username,String password){
        // ...
        return false;
        }
```

**Getters**

Getters are operations that return the value of a field. You should prefix the word get to the name
of the field, unless it is a **boolean** field and then you prefix **is** to the name of the field
instead of get.

```java
private boolean active;
private String email;

public boolean isActive(){
        return active;
        }

public String getEmail(){
        return email;
        }
```

**Setters**

Setters, also known as mutators, are operations that modify the values of a field. You should prefix
the word **set** to the name of the field, regardless of the field type.

```java
public void setActive(boolean active){
        this.active=active;
        }

public void setEmail(String email){
        this.email=email;
        }
```

**Collections Naming Accessors**

 Member Function Type                            | Naming Convention | Example             
-------------------------------------------------|-------------------|---------------------
 Getter for the collection                       | `getCollection()` | `getOrderItems()`   
 Setter for the collection                       | `setCollection()` | `setOrderItems()`   
 Insert an object into the collection            | `insertObject()`  | `insertOrderItem()` 
 Delete an object from the collection            | `deleteObject()`  | `deleteOrderItem()` 
 Create and add a new object into the collection | `newObject()`     | `newOrderItem()`    

The advantage of this approach is that the collection is fully encapsulated, allowing you to later
replace it with another structure, perhaps a linked list or a B-tree.

**Operation Parameters Naming**

Parameters should be named following the exact same conventions as for local variables and fields.
Use the word this as a prefix of field name to avoid the name hiding problem in case the parameter
name is the same with field name.

```java
private Command command;
public void execute(Command command){
        this.command=command;
        // ...
        }
```

### Field Naming

You should use a full English descriptor to name your fields to make it obvious what the field
represents. Fields that are collections, such as arrays or vectors, should be given names that are
plural to indicate that they represent multiple values. All first letters of non-initial words
should be capitalized.

```java
private String firstName;
private String zipCode;
private BigDecimal unitPrice;
private double discountRate;
private List<OrderItem> orderItems;
```

**Constants Naming**
In Java, constants, values that do not change, are typically implemented as static final fields of
classes. The recognized convention is to use full English words, all in uppercase, with underscores
between the words.

```java
public static final double MINIMUM_BALANCE=10.0;
public static final int MAX_FILE_SIZE=10*1024;
public static final Date DEFAULT_START_DATE=new Date();
```

Replace all Magic Numbers / Strings with named constants to give them a meaningful name when meaning
cannot be derived from the value itself.

For the class that implemented **Serializable** interface, the **serialVersionUID** variable should
be followed the form:

```java
private static final long serialVersionUID=<yyyyMMdd>L;
```

For the logger, it should be followed the form:

```java
private static final Logger Logger=LoggerFactory.getLogger(<ClassName>.class);
```

### Local Variable Naming

In general, local variables are named following the same conventions as used for fields, in other
words use full English descriptors with the first letter of any non-initial word in uppercase.

Don’t use a local variable that has the same name as a class member variable.

**Streams Naming**

When there is a single input and/or output stream being opened, used, and then closed within a
member function the common convention is to use **input** and **output** for the names of these
streams, respectively.

```java
InputStream input=null;
        OutputStream output=null;
        try{
        input=new FileInputStream("...");
        output=new FileOutputStream("...");

        IOUtils.copy(input,output);
        }catch(IOException e){
        // ...
        }finally{
        IOUtils.cLoseQuietLy(output);
        IOUtils.cLoseQuietLy(input);
        }
```

**Loop Counters Naming**

Because loop counters are a very common use for local variables, and because it was acceptable in
C/C++, in Java programming the use of **i**, **j**, or **k**, is acceptable for loop counters. If
you use these names for loop counters, use them consistently.

```java
for(int i=1;i<MAX_ROWS; i++){
        for(int j=1;j<MAX_COLUMNS; j++){
        // ... 
        }
        // ...
        }
```

**Exception Objects Naming**

Because exception handling is also very common in Java coding the use of the letter **e** for a
generic exception is considered acceptable.

```java
try{
        // ...
        }catch(Exception e){
        // ...
        }
```

**Collections Naming**

A collection, such as an array or a vector, should be given a pluralized name representing the types
of objects stored by the array. The name should be a full English descriptor with the first letter
of all non-initial words capitalized.

For a map, It should be followed the pattern **valuesByKeys**

```java
List<User> users=new ArrayList<User>();
        Command[]commands=new Command[MAX_COMMANDS];
        Map<String, User> usersByEmails=new HashMap<String, User>();
```

### Exception Naming

Exceptions should be name with a short name describing the error suffixed by the word **Exception**

```java
public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 20131210L;

    // ...
}

public class AuthenticationFailedException extends Exception {
    private static final long serialVersionUID = 20131210L;

    // ...
}
```