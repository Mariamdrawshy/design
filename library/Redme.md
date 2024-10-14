# Packages
 - Library
 - Search
 - State


## Library
## `class Library`:

The `Library` class represents a library. It contains information about the library's name, description, books, and members. The class provides methods to add and remove books and members, lend and return books, search for books, and retrieve summary information about the library.

### Features

- **Singleton Pattern**: Ensures only one instance of the library exists.
- **Book Management**: Add, remove, lend, and return books.
- **Member Management**: Add, remove, and retrieve members.
- **Search Functionality**: Search for books based on name, author, and year of publication.
- **Summary Information**: Retrieve a summary of the library's current state.

### Methods

#### Singleton Instance

- `public static Library getInstance()`: Returns the single instance of the library.

#### Library Information

- `public String getName()`: Gets the library's name.
- `public void setName(String name)`: Sets the library's name.
- `public String getDescription()`: Gets the library's description.
- `public void setDescription(String description)`: Sets the library's description.

#### Book Management

- `public List<Book> getBooks()`: Gets the list of books in the library.
- `public void setBooks(List<Book> books)`: Sets the list of books in the library.
- `public void addBook(Book book)`: Adds a book to the library.
- `public void removeBook(Book book)`: Removes a book from the library.
- `public void lendBook(Book book)`: Lends a book to a member.
- `public void returnBook(Book book)`: Returns a book to the library.

#### Member Management

- `public List<Member> getMembers()`: Gets the list of members in the library.
- `public void setMembers(List<Member> members)`: Sets the list of members in the library.
- `public void addMember(Member member)`: Adds a member to the library.
- `public void removeMember(Member member)`: Removes a member from the library.
- `public Member getMemberByID(String ID)`: Retrieves a member by their ID.

#### Search Functionality

- `public List<Book> searchBooks(String query)`: Searches for books based on the provided query.

#### Summary Information

- `public String getSummary()`: Retrieves a summary of the library's current state.

## Book Class

The `Book` class represents a book in the library. It contains information about the book's name, author, year of publication, and availability status. The class provides methods to get and set these properties, as well as a method to clone the book.

### Features

- **Cloneable**: Implements the `Cloneable` interface to allow cloning of book objects.
- **Book Information**: Get and set the book's name, author, year of publication, and availability status.

### Constructor

- `public Book(String name, String author, int yearOfPublication)`: Initializes a new book with the specified name, author, and year of publication. The book is available by default.

### Methods

#### Cloning

- `public Book clone()`: Creates and returns a copy of this book.

#### Getters and Setters

- `public String getName()`: Gets the book's name.
- `public void setName(String name)`: Sets the book's name.
- `public String getAuthor()`: Gets the book's author.
- `public void setAuthor(String author)`: Sets the book's author.
- `public int getYearOfPublication()`: Gets the book's year of publication.
- `public void setYearOfPublication(int yearOfPublication)`: Sets the book's year of publication.
- `public boolean isAvailable()`: Checks if the book is available.
- `public void setAvailable(boolean isAvailable)`: Sets the book's availability status.


## Member Class

The `Member` class represents a member of the library. It contains information about the member's name, ID, borrowed books, questions, and state. The class provides methods to manage borrowed books, ask questions, and interact with the librarian.

### Features

- **Member Information**: Get the member's name and ID.
- **Book Management**: Add and remove borrowed books.
- **Question Management**: Add and retrieve questions.
- **State Management**: Manage the member's state (active, banned, suspended).
- **Interactions with Librarian**: Ask to borrow and return books.

### Constructor

- `public Member(String name, String ID)`: Initializes a new member with the specified name and ID. The member starts with an empty list of borrowed books and questions, and is in the active state.

### Methods

#### Member Information

- `public String getName()`: Gets the member's name.
- `public String getID()`: Gets the member's ID.

#### Question Management

- `public List<String> getQuestions()`: Gets the list of questions asked by the member.
- `public void addQuestion(String question, String additionalInfo)`: Adds a question to the member's list of questions. Throws an `IllegalArgumentException` if the question is null, empty, or already exists.
- `public void setQuestions(List<String> questions)`: Sets the list of questions for the member.

#### Book Management

- `public List<Book> getBorrowedBooks()`: Gets the list of books borrowed by the member.
- `public void addBorrowedBook(Book book)`: Adds a book to the member's list of borrowed books.
- `public void removeBorrowedBook(Book book)`: Removes a book from the member's list of borrowed books.

#### Interactions with Librarian

- `public void askToBorrowBook(Librarian librarian, Book book)`: Asks the librarian to borrow a book. The action is handled based on the member's current state.
- `public void returnBorrowedBook(Librarian librarian, Book book)`: Returns a borrowed book to the librarian. The action is handled based on the member's current state.

#### State Management

- `public void setState(MemberState state)`: Sets the member's state.
- `public boolean isActive()`: Checks if the member is in the active state.
- `public boolean isBanned()`: Checks if the member is in the banned state.
- `public boolean isSuspended()`: Checks if the member is in the suspended state.

## Librarian Class
The Librarian class is part of the com.example.library.Library package and provides functionalities to manage a library system. It allows for the creation, addition, and removal of books and members, as well as lending and returning books. The class also supports member management, including banning, unbanning, suspending, and unsuspending members. Additionally, it provides search functionalities for books using various search strategies.

### Class Overview
### Constructor
 - `Librarian(String name, String id)`: Initializes a new librarian with the given name and ID.
### Methods
- void addBook(String title, String author, int yearOfPublication): Adds a new book to the library.
- void removeBook(Book book): Removes a book from the library.
- void addMember(String name, String memberId): Adds a new member to the library.
- void removeMember(Member member): Removes a member from the library.
- String getName(): Returns the name of the librarian.
- String getId(): Returns the ID of the librarian.
- void lendBook(Book book, Member member): Lends a book to a member.
- void returnBook(Book book, Member member): Returns a book from a member.
- void banMember(Member member): Bans a member.
- void unbanMember(Member member): Unbans a member.
- void suspendMember(Member member, int days): Suspends a member for a specified number of days.
- void unsuspendMember(Member member): Unsuspends a member.
- Member getMemberByID(String ID): Retrieves a member by their ID.
- String getSummary(): Returns a summary of the library.
- List<Book> searchBooks(SearchStrategy strategy): Searches for books using a specified search strategy.
- List<Book> searchBooks(String query): Searches for books using multiple search parameters.


# Utilities
## Here is a sample README file for the `SearchStrategy` interface:

# SearchStrategy Interface

The `SearchStrategy` interface is part of the `com.example.library.Search` package and defines a contract for implementing various book search strategies within a library system. This interface allows for flexible and extensible search functionalities by enabling different search criteria to be applied to books.

## Interface Overview

### Method

- `boolean matches(Book book, String query)`: Determines whether a given book matches the search criteria specified by the query.

### Method Details

- `boolean matches(Book book, String query)`: 
  - **Parameters**:
    - `Book book`: The book to be evaluated against the search criteria.
    - `String query`: The search query containing the criteria to match.
  - **Returns**: 
    - `true` if the book matches the search criteria specified by the query.
    - `false` otherwise.


## Dependencies

- `com.example.library.Library.*`: Contains the `Book` class and other library-related classes.

### Stretegies 
 - Search by Name, Author, Year seperatly
 - Composite Searching combing all the params


## State
- Active
- Banned
- Suspended

