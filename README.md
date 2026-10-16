<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=6,11,20&height=220&section=header&text=Mobile%20App%20Development&fontSize=48&fontColor=ffffff&animation=fadeIn&fontAlignY=36&desc=Coursework%20%E2%80%A2%20Labs%20%E2%80%A2%20Assignments%20%E2%80%A2%20Android%20Studio&descSize=17&descAlignY=58" alt="Mobile App Development Coursework" width="100%"/>

<br/>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![XML](https://img.shields.io/badge/XML-005FAD?style=for-the-badge&logo=xml&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-6750A4?style=for-the-badge&logo=materialdesign&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

![Min SDK](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-brightgreen?style=flat-square&logo=android)
![Target SDK](https://img.shields.io/badge/Target%20SDK-37-blue?style=flat-square&logo=android)
![Projects](https://img.shields.io/badge/Projects-3-ff69b4?style=flat-square)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow?style=flat-square)
![Last Commit](https://img.shields.io/github/last-commit/furqanzubair209-cell/mobile-app-development-coursework?style=flat-square&color=blueviolet)
![Repo Size](https://img.shields.io/github/repo-size/furqanzubair209-cell/mobile-app-development-coursework?style=flat-square&color=orange)

**Three standalone Android Studio projects, one repository.**
From a Compose starter, to a ListView vs RecyclerView showdown, to a full fragment-based food-delivery app with a working voucher system.

</div>

---

## ✨ About

This repository holds the lab tasks and assignments from my **Mobile App Development** course at The Superior University, Lahore. Every folder is a **self-contained Android Studio project** with its own Gradle wrapper, so you can open any one of them directly without touching the others.

The projects are deliberately arranged as a progression:

| Stage | What it teaches | Project |
|:-:|---|---|
| 🟢 **1** | Project setup, modern declarative UI with Compose | **MyApplication** |
| 🔵 **2** | Displaying collections: `ListView` vs `RecyclerView`, custom adapters, data models | **Lists** |
| 🔴 **3** | A complete app: auth flow, fragment navigation, shared state, vouchers, adapters, sample data | **Foodie** |

---

## 📦 Projects at a Glance

<table>
<tr>
<td width="33%" valign="top">

### 🍔 Foodie
**Food-delivery app**

`Java` `XML` `Material` `Fragments` `RecyclerView`

8-tab fragment UI · 6 restaurants · 24 dishes · live search · voucher engine · working cart and order flow.

Best for seeing how screens, shared state and data come together in one app.

</td>
<td width="33%" valign="top">

### 📱 Lists
**ListView vs RecyclerView**

`Java` `XML` `Material 3`

20 flagship smartphones shown two ways, side by side, with brand logos and ratings.

Best for understanding how the two list approaches differ in practice.

</td>
<td width="33%" valign="top">

### 🧩 MyApplication
**Jetpack Compose starter**

`Kotlin` `Compose` `Material 3`

Edge-to-edge Compose scaffold with a themed Material 3 setup.

Best for seeing the modern declarative way to build Android UI.

</td>
</tr>
</table>

| Folder | Language | UI Toolkit | Package | Highlights |
|---|---|---|---|---|
| `Foodapp` | Java | XML + Material Components | `com.example.foodapp` | Login-first flow, 8-tab fragment shell, cart singleton, vouchers |
| `Lists` | Java | XML + Material Components | `com.example.lists` | Two adapters, one dataset, direct comparison |
| `MyApplication` | Kotlin | Jetpack Compose | `com.example.myapplication` | Compose + Material 3 theming, edge-to-edge |

---

## 🍔 Foodie — Food Delivery App

> A Foodpanda-inspired ordering app with a pink brand palette, built entirely with Java, XML layouts and Material Components. The app opens on a login screen, then drops into a **tabbed, fragment-based home shell** where browsing, searching, offers, cart, orders, favorites and profile all live one tap apart. All data is local sample data, so it runs with **no backend and no API keys**.

### 🎯 Features

- 🚀 **Splash screen** with a timed hand-off straight into the **Login** screen
- 🔐 **Login and Sign-up** screens using Material `TextInputLayout`, scrollable on small screens, with inline validation (required fields, `@` check, minimum 4-character password, password confirmation on sign-up)
- 🧭 **8-tab fragment shell**: Home, Restaurants, Search, Offers, Cart, Orders, Favorites and Profile, in a scrollable pill tab bar that auto-centers the selected tab
- 🏠 **Home tab** with delivery address, search bar, promo banner, category shortcuts (Pizza, Burgers, Sushi, Noodles, Desserts, Drinks), popular dishes and featured restaurants
- 🛍️ **Live cart badge** in the header that updates instantly from anywhere in the app
- 🔍 **Live search** that filters every dish by name, description and category as you type
- 🏪 **Restaurant browsing and detail pages** with menu items, ratings, delivery time, fee and price tier
- 🍽️ **Food detail page** with quantity selection and add-to-cart
- 🛒 **Shopping cart** with quantity controls, remove and clear, plus live subtotal, delivery fee, voucher discount and total
- 🎟️ **Working voucher engine**: apply a code on the Offers tab and the Cart tab recalculates immediately
- 📦 **Order placement** that adds a new order to history and jumps to the Orders tab from a confirmation dialog
- 🔁 **Order history** with status chips and one-tap **reorder**
- ❤️ **Favorites tab** with a curated list of top dishes, quick-add and detail navigation
- 👤 **Profile tab** with addresses, payment shortcut, orders link and a logout that clears the back stack

### 🎟️ Voucher Engine

Vouchers are applied through the shared `CartManager`, so every screen sees the same discount.

| Code | Effect | How it is calculated |
|---|---|---|
| `FOODIE50` | **50% off** the subtotal | `subtotal × 0.50` |
| `BURGER20` | **20% off** the subtotal | `subtotal × 0.20` |
| `FREEDEL` | **Free delivery** | Delivery fee drops from `$2.49` to `$0.00` |

- The Offers tab shows an applied voucher as **"✓ CODE Applied"** and disables the button, and restores that state when you come back.
- The Cart tab shows a dedicated discount row only when a voucher is active.
- The total is clamped so it can **never go below zero**.

### 🗺️ Navigation Flow

```mermaid
flowchart LR
    A([Splash]) --> B[Login]
    B <--> C[Sign Up]
    B --> D{{Main Shell<br/>8 tabs}}
    C --> D

    D --> T1[Home]
    D --> T2[Restaurants]
    D --> T3[Search]
    D --> T4[Offers]
    D --> T5[Cart]
    D --> T6[Orders]
    D --> T7[Favorites]
    D --> T8[Profile]

    T1 --> R[Restaurant Details]
    T2 --> R
    R --> F[Food Details]
    T1 --> F
    T3 --> F
    T7 --> F
    F --> T5

    T4 -->|Apply voucher| T5
    T5 -->|Place Order| T6
    T6 -->|Reorder| T5
    T8 -->|Logout| B

    style A fill:#D70F64,color:#fff,stroke:none
    style D fill:#FF2B85,color:#fff,stroke:none
    style T5 fill:#B00B50,color:#fff,stroke:none
```

### 🏗️ Architecture

```mermaid
flowchart TB
    subgraph SHELL["Shell: MainActivity"]
        MA[Tab bar · cart badge · fragment container]
    end
    subgraph FR["UI layer: 8 Fragments"]
        FRG[Home · Restaurants · Search · Offers<br/>Cart · Orders · Favorites · Profile]
    end
    subgraph ACT["Standalone Activities"]
        A1[Splash · Login · SignUp]
        A2[RestaurantDetails · FoodDetails · Cart · legacy screens]
    end
    subgraph AD["Adapters: 5 RecyclerView adapters"]
        ADP[Food · PopularFood · Restaurant · Cart · Order]
    end
    subgraph DATA["Data layer"]
        CM[CartManager<br/>singleton · listeners · vouchers]
        SD[SampleData<br/>restaurants, dishes, orders]
    end
    subgraph MODEL["Models"]
        M[FoodItem · Restaurant · CartItem · Order · Category]
    end
    MA --> FRG
    FRG --> ADP
    FRG --> CM
    FRG --> SD
    FRG --> ACT
    A2 --> CM
    ADP --> M
    CM --> M
    SD --> M
```

### 🧠 Concepts Demonstrated

| Concept | Where it shows up |
|---|---|
| **Fragment-based navigation** | `MainActivity` swaps 8 fragments inside one container and keeps the tab bar in sync |
| **Singleton + observer pattern** | `CartManager` notifies registered listeners (cart badge, cart tab) whenever the cart changes |
| **Shared state as a single source of truth** | Cart, voucher and totals live in one place, so features like vouchers plug in cleanly |
| **RecyclerView + custom adapters** | Five adapters with click callbacks for item taps and quick-add |
| **Explicit intents + extras** | Passing a `FoodItem` / `Restaurant` from list screens to detail screens |
| **Form validation** | Login and sign-up error states on `TextInputLayout` |
| **Back-stack control** | `FLAG_ACTIVITY_CLEAR_TASK` on logout so Back never returns to a signed-in screen |
| **Model layer separation** | `model/`, `adapter/`, `data/` and `fragment/` packages kept apart from activities |
| **Custom drawables** | Vector icons plus shape backgrounds for badges, chips, cards, tabs and buttons |
| **Day and night themes** | `values` and `values-night` on a Material 3 DayNight base |

### 📊 Project Stats

| Metric | Count |
|---|---|
| Activities | 12 |
| Fragments | 8 |
| RecyclerView adapters | 5 |
| Model classes | 5 |
| Layout files | 25 (12 activity · 8 fragment · 5 list item) |
| Restaurants · Dishes · Sample orders | 6 · 24 · 3 |
| Vector icons · Shape backgrounds | 23 · 10 |
| Photo assets | 52 JPG (categories, dishes, restaurants, slider, clients) |

### 📁 Structure

```text
Foodapp/app/src/main/
├── java/com/example/foodapp/
│   ├── SplashActivity.java          LoginActivity.java       SignUpActivity.java
│   ├── MainActivity.java            tab shell hosting the 8 fragments
│   ├── RestaurantDetailsActivity    FoodDetailsActivity      CartActivity.java
│   ├── RestaurantsActivity.java     SearchActivity.java      OffersActivity.java
│   ├── OrdersActivity.java          ProfileActivity.java
│   ├── fragment/                    Home · Restaurants · Search · Offers
│   │                                Cart · Orders · Favorites · Profile
│   ├── adapter/                     5 RecyclerView adapters
│   ├── data/                        CartManager (cart + vouchers) · SampleData
│   └── model/                       CartItem · Category · FoodItem · Order · Restaurant
└── res/
    ├── layout/                      activity_* · fragment_* · item_*
    ├── drawable/                    vector icons + shape backgrounds + photos
    └── values/                      colors · strings · themes (+ values-night)
```

### 🎨 Palette

| Token | Hex | Use |
|---|---|---|
| ![#D70F64](https://img.shields.io/badge/-D70F64-D70F64?style=flat-square) `foodie_pink` | `#D70F64` | Primary brand |
| ![#B00B50](https://img.shields.io/badge/-B00B50-B00B50?style=flat-square) `foodie_pink_dark` | `#B00B50` | Pressed and dark states |
| ![#FF2B85](https://img.shields.io/badge/-FF2B85-FF2B85?style=flat-square) `foodie_pink_accent` | `#FF2B85` | Accents |
| ![#FFF0F5](https://img.shields.io/badge/-FFF0F5-FFF0F5?style=flat-square) `foodie_pink_light` | `#FFF0F5` | Soft backgrounds |

---

## 📱 Lists — ListView vs RecyclerView

> One dataset, two implementations. The same 20 flagship smartphones are rendered with a classic `ListView` + `BaseAdapter` and with a modern `RecyclerView` + `ViewHolder`, so the differences are easy to see in code and in behavior.

### 🎯 Features

- 🏁 **Landing screen** with two entry cards, one per list technique
- 📋 **ListView screen** powered by `ListViewAdapter` and `item_gadget.xml`
- ⚡ **RecyclerView screen** powered by `RecyclerAdapter` with a proper `ViewHolder` and `item_gadget_recycler.xml`
- 📱 **20 flagship phones** (iPhone, Galaxy, Pixel, Xperia, OnePlus, Xiaomi and more), each with description, category badge, star rating and review count
- 🏷️ **20 custom vector brand logos** drawn as XML drawables, so there are no image assets and everything scales cleanly
- 🎨 **Indigo and violet Material 3 styling** with gradient headers, category badges and spec pills
- 📐 **Edge-to-edge** layout with window insets handled in code

### 🔬 The Comparison

| | `ListView` | `RecyclerView` |
|---|---|---|
| **Adapter** | `ListViewAdapter` | `RecyclerAdapter` |
| **Item layout** | `item_gadget.xml` | `item_gadget_recycler.xml` |
| **View recycling** | Manual (`convertView`) | Built in via `ViewHolder` |
| **Layout flexibility** | Vertical list only | Pluggable `LayoutManager` |
| **Screen** | `ListViewActivity` | `RecyclerActivity` |

### 📁 Structure

```text
Lists/app/src/main/
├── java/com/example/lists/
│   ├── MainActivity.java            entry screen, routes to both demos
│   ├── ListViewActivity.java        ListView demo
│   ├── RecyclerActivity.java        RecyclerView demo
│   ├── ListViewAdapter.java         BaseAdapter implementation
│   ├── RecyclerAdapter.java         RecyclerView.Adapter + ViewHolder
│   ├── Gadget.java                  model: name, description, category, rating, reviews, icon
│   └── GadgetProvider.java          the 20-device dataset
└── res/
    ├── layout/                      activity_main · activity_list_view · activity_recycler · item_gadget · item_gadget_recycler
    └── drawable/                    20 ic_logo_*.xml brand vectors + gradient/badge/pill backgrounds
```

---

## 🧩 MyApplication — Jetpack Compose Starter

> The first project of the course: a clean Android Studio **Empty Activity (Compose)** scaffold, used to set up the toolchain and explore the declarative UI model.

### 🎯 What's inside

- 🧱 `ComponentActivity` hosting a Compose UI through `setContent`
- 📐 `enableEdgeToEdge()` with a `Scaffold` that applies inner padding
- 🎨 Material 3 theme split into `Color.kt`, `Type.kt` and `Theme.kt`
- 👀 `@Preview` composable for instant previews inside Android Studio
- 🧪 Compose UI test and tooling dependencies wired up through the Compose BOM

### 🛠️ Stack

| Component | Version / Choice |
|---|---|
| Language | Kotlin `2.2.10` |
| UI | Jetpack Compose (BOM `2026.02.01`) + Material 3 |
| Android Gradle Plugin | `9.4.0` |
| Min / Target SDK | `24` / `37` |

### 📁 Structure

```text
MyApplication/app/src/main/java/com/example/myapplication/
├── MainActivity.kt        Compose entry point + Greeting composable
└── ui/theme/
    ├── Color.kt
    ├── Type.kt
    └── Theme.kt
```

---

## 🛠️ Tech Stack

| Area | Technologies |
|---|---|
| **Languages** | Java 11, Kotlin 2.2 |
| **UI** | XML layouts, Material Components 1.14, Jetpack Compose, Material 3 |
| **Navigation** | Fragment transactions inside a tab shell, explicit intents with extras, back-stack flags |
| **Lists and data display** | `ListView`, `RecyclerView`, `BaseAdapter`, `RecyclerView.Adapter`, `ViewHolder` |
| **Patterns** | Singleton, observer (listeners), model/adapter/data separation |
| **Build** | Gradle Kotlin DSL, version catalogs (`libs.versions.toml`), Android Gradle Plugin 9.x |
| **Testing scaffold** | JUnit 4, Espresso, AndroidX Test |
| **IDE** | Android Studio |

---

## 📂 Repository Structure

```text
mobile-app-development-coursework/
├── 🍔 Foodapp/            Food-delivery app (Java + XML + Fragments)
├── 📱 Lists/              ListView vs RecyclerView (Java + XML)
├── 🧩 MyApplication/      Jetpack Compose starter (Kotlin)
└── 📄 README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (recent stable release)
- **JDK 11+** (bundled with Android Studio)
- An emulator or physical device running **Android 7.0 (API 24) or higher**

### Run a project

```bash
# 1. Clone the repository
git clone https://github.com/furqanzubair209-cell/mobile-app-development-coursework.git

# 2. Open ONE of the project folders in Android Studio
#    Foodapp  |  Lists  |  MyApplication
```

3. Wait for the **Gradle sync** to finish.
4. Pick an emulator or connect a device, then press **▶ Run**.

> 💡 Each folder is its own Gradle project. Open `Foodapp`, `Lists` or `MyApplication` directly, **not** the repository root.

### Try Foodie without Android Studio

Download the debug APK from the **Releases** page, allow *Install unknown apps* for your browser or file manager, then open the file on any Android 7.0+ phone.

> 🔑 Login and sign-up are **client-side only** (no backend), so any valid-looking email and a password of 4+ characters will sign you in. Try the vouchers `FOODIE50`, `BURGER20` and `FREEDEL` from the **Offers** tab.

---

## 🗓️ Roadmap

- [x] Jetpack Compose starter project
- [x] ListView vs RecyclerView comparison
- [x] Multi-screen food-delivery app
- [x] Fragment-based tab navigation for Foodie
- [x] Voucher engine with live cart totals
- [ ] Persist cart, orders and login locally (Room or SharedPreferences)
- [ ] Replace sample data with a remote API
- [ ] Add UI tests for the Foodie order flow
- [ ] Upcoming lab tasks and assignments

---

## 👨‍💻 Author

<div align="center">

**Muhammad Furqan**  
Computer Science Student · The Superior University, Lahore

**GitHub:** [furqanzubair209-cell](https://github.com/furqanzubair209-cell) — source code for coursework, personal projects and experiments.  
**LinkedIn:** [muhammad-furqan-228807304](https://www.linkedin.com/in/muhammad-furqan-228807304) — professional profile, certificates and project updates.  
**Portfolio:** [furqannewportfolio.netlify.app](https://furqannewportfolio.netlify.app) — 28 projects across frontend, AI/ML and automation.

<br/>

⭐ If you found this useful, consider starring the repository.

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=6,11,20&height=120&section=footer" alt="footer" width="100%"/>

</div>
