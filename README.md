# 🌱 Bæredygtig Ønskesky

## 📌 Projektbeskrivelse

Dette projekt er en bæredygtig version af den klassiske ønskesky-platform. Ideen er at give brugerne mulighed for at oprette, dele og gemme ønsker – men kun hvis de er bæredygtige.

Målet er at fremme mere miljøvenlige valg i hverdagen ved at inspirere til ønsker, der tager hensyn til klima, ressourcer og ansvarligt forbrug.

---

## 🎯 Formål

* At gøre det nemt at finde og dele bæredygtige produkter og idéer
* At reducere overforbrug og engangsprodukter
* At skabe en platform, hvor bæredygtighed er standarden

---

## 🌍 Funktioner

* Opret brugere og profiler
* Tilføj ønsker til din ønskeliste
* Del ønsker med venner
* Like og gem andres bæredygtige ønsker

##

---

## 🛠️ Teknologi

* Frontend: Localhost:8080
* Backend:Java/SpringBoot
* Database: MySQL

---

## 🚀 Installation

```bash
# Klon repository
git clone https://github.com/study-group-supreme/Wishlist.git

# Gå ind i projektet
cd Wishlist

# Installer dependencies
./mvnw install

# Start udviklingsserver
./mvnw spring-boot:run
```

---

## 📁 Projektstruktur

```
├── mvnw
├── mvnw.cmd
├── pom.xml
├── qodana.yaml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── wishlist
│   │   │       ├── config
│   │   │       │   └── WebConfig.java
│   │   │       ├── controller
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   ├── MemberController.java
│   │   │       │   ├── PublicViewController.java
│   │   │       │   └── WishlistController.java
│   │   │       ├── exception
│   │   │       │   ├── BadRequestException.java
│   │   │       │   ├── DatabaseOperationException.java
│   │   │       │   ├── DuplicateMemberException.java
│   │   │       │   └── NotFoundException.java
│   │   │       ├── interceptor
│   │   │       │   └── LoginInterceptor.java
│   │   │       ├── model
│   │   │       │   ├── Item.java
│   │   │       │   ├── Member.java
│   │   │       │   └── Wishlist.java
│   │   │       ├── repository
│   │   │       │   ├── ItemRepository.java
│   │   │       │   ├── MemberRepository.java
│   │   │       │   └── WishlistRepository.java
│   │   │       ├── service
│   │   │       │   ├── ItemService.java
│   │   │       │   ├── MemberService.java
│   │   │       │   └── WishlistService.java
│   │   │       └── WishlistApplication.java
│   │   └── resources
│   │       ├── application-dev.properties
│   │       ├── application.properties
│   │       ├── sql
│   │       │   ├── data.sql
│   │       │   └── schema.sql
│   │       ├── static
│   │       │   ├── images
│   │       │   │   ├── greenwish-favicon.png
│   │       │   │   └── WISHLIST_LOGO.png
│   │       │   ├── Main.css
│   │       │   └── style.css
│   │       └── templates
│   │           ├── error
│   │           │   └── 404.html
│   │           ├── fragments
│   │           │   └── header.html
│   │           ├── member
│   │           │   ├── member-edit.html
│   │           │   ├── member-login.html
│   │           │   ├── member-registration.html
│   │           │   └── placeholder.html
│   │           ├── public
│   │           │   ├── public-aboutUs.html
│   │           │   └── public-homepage.html
│   │           └── wishlist
│   │               ├── add-item.html
│   │               ├── create-wishlist.html
│   │               ├── details.html
│   │               ├── edit-item.html
│   │               ├── edit-wishlist.html
│   │               └── list.html
│   └── test
│       ├── java
│       │   └── wishlist
│       │       ├── controller
│       │       │   ├── AuthControllerTest.java
│       │       │   ├── MemberControllerTest.java
│       │       │   ├── PublicViewControllerTest.java
│       │       │   └── WishlistControllerTest.java
│       │       ├── integration
│       │       │   ├── ItemRepositoryTest.java
│       │       │   ├── MemberRepositoryTest.java
│       │       │   └── WishlistRepositoryTest.java
│       │       ├── service
│       │       │   ├── ItemServiceTest.java
│       │       │   ├── MemberServiceTest.java
│       │       │   └── WishlistServiceTest.java
│       │       └── WishlistApplicationTests.java
│       └── resources
│           ├── application-test.properties
│           └── h2init.sql
└── target
    ├── classes
    │   ├── application-dev.properties
    │   ├── application.properties
    │   ├── sql
    │   │   ├── data.sql
    │   │   └── schema.sql
    │   ├── static
    │   │   ├── images
    │   │   │   ├── greenwish-favicon.png
    │   │   │   ├── Trees.png
    │   │   │   └── WISHLIST_LOGO.png
    │   │   ├── Main.css
    │   │   └── style.css
    │   ├── templates
    │   │   ├── error
    │   │   │   └── 404.html
    │   │   ├── fragments
    │   │   │   └── header.html
    │   │   ├── member
    │   │   │   ├── member-edit.html
    │   │   │   ├── member-login.html
    │   │   │   ├── member-registration.html
    │   │   │   └── placeholder.html
    │   │   ├── public
    │   │   │   ├── public-aboutUs.html
    │   │   │   └── public-homepage.html
    │   │   └── wishlist
    │   │       ├── add-item.html
    │   │       ├── create-wishlist.html
    │   │       ├── details.html
    │   │       ├── edit-item.html
    │   │       ├── edit-wishlist.html
    │   │       └── list.html
    │   └── wishlist
    │       ├── config
    │       │   └── WebConfig.class
    │       ├── controller
    │       │   ├── AuthController.class
    │       │   ├── GlobalExceptionHandler.class
    │       │   ├── MemberController.class
    │       │   ├── PublicViewController.class
    │       │   └── WishlistController.class
    │       ├── exception
    │       │   ├── BadRequestException.class
    │       │   ├── DatabaseOperationException.class
    │       │   ├── DuplicateMemberException.class
    │       │   └── NotFoundException.class
    │       ├── interceptor
    │       │   └── LoginInterceptor.class
    │       ├── model
    │       │   ├── Item.class
    │       │   ├── Member.class
    │       │   └── Wishlist.class
    │       ├── repository
    │       │   ├── ItemRepository.class
    │       │   ├── MemberRepository.class
    │       │   └── WishlistRepository.class
    │       ├── service
    │       │   ├── ItemService.class
    │       │   ├── MemberService.class
    │       │   └── WishlistService.class
    │       └── WishlistApplication.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    └── test-classes
        ├── application-test.properties
        ├── h2init.sql
        └── wishlist
            ├── controller
            │   ├── AuthControllerTest.class
            │   ├── MemberControllerTest.class
            │   ├── PublicViewControllerTest.class
            │   └── WishlistControllerTest.class
            ├── integration
            │   ├── ItemRepositoryTest.class
            │   ├── MemberRepositoryTest.class
            │   └── WishlistRepositoryTest.class
            ├── service
            │   ├── ItemServiceTest.class
            │   ├── MemberServiceTest.class
            │   └── WishlistServiceTest.class
            └── WishlistApplicationTests.class
```

---

## 🌱 Fremtidige forbedringer

* Integration med grønne webshops

---

## 🤝 Bidrag

Pull requests er meget velkomne! Hvis du vil bidrage, så:

1. Fork repoet
2. Lav en feature branch
3. Commit dine ændringer
4. Lav en pull request

---

Dette projekt er open source under MIT-licensen.

---

💚 Lavet med fokus på en grønnere fremtid
