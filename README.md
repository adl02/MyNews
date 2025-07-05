# 📲 MyNews - Jetpack Compose News App

MyNews is a modern, clean, and lightweight news app built using **Jetpack Compose**, **NewsAPI**, **Room**, and **MVVM architecture**. It delivers the latest headlines in under 50 words with an intuitive and minimal interface.

---

## 🚀 Features

- ✅ Onboarding screen for first-time users
- 📰 Latest news powered by [NewsAPI.org](https://newsapi.org)
- ♾️ Infinite scroll with paginated API fetch
- ♻️ **Swipe down to refresh feed manually**
- 🧠 Intelligent random article display (no repeats per session)
- ❤️ Like and save articles (persisted via Room database)
- 📦 Offline Room support for liked news
- 🌙 Dark/Light Theme support
- 📤 Share articles
- 🎬 Image and video support (via Coil + ExoPlayer)
- 📱 Built fully using Jetpack Compose

---

## 🛠 Tech Stack

| Layer         | Tools & Libraries                            |
|---------------|----------------------------------------------|
| UI            | Jetpack Compose, Material3, Accompanist      |
| Architecture  | MVVM, StateFlow, ViewModel                   |
| Backend/API   | NewsAPI.org via custom Retrofit wrapper      |
| Local Storage | Room Database                                |
| Navigation    | Navigation Compose                           |
| State Saving  | DataStore Preferences                        |
| Media         | Coil (Image), ExoPlayer (Video)              |

---

## 📱 Screenshots

### 🟣 Splash & Onboarding

<table>
  <tr>
    <td><b>Splash</b></td>
    <td><b>Onboarding 1</b></td>
    <td><b>Onboarding 2</b></td>
    <td><b>Onboarding 3</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/splash.jpg" width="200"/></td>
    <td><img src="screenshots/onboarding1.jpg" width="200"/></td>
    <td><img src="screenshots/onboarding2.jpg" width="200"/></td>
    <td><img src="screenshots/onboarding3.jpg" width="200"/></td>
  </tr>
</table>

---

### 🟢 Home & Category

<table>
  <tr>
    <td><b>Home</b></td>
    <td><b>Category</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/home.jpg" width="250"/></td>
    <td><img src="screenshots/category.jpg" width="250"/></td>
  </tr>

</table>

---

### 🔵 Features

<table>
  <tr>
    <td><b>Saved Articles</b></td>
    <td><b>Share Button</b></td>
    <td><b>Share Message</b></td>
  </tr>
  <tr>
    <td><img src="screenshots/saved.jpg" width="200"/></td>
    <td><img src="screenshots/share_button.jpg" width="200"/></td>
    <td><img src="screenshots/share_msg.jpg" width="200"/></td>
  </tr>
</table>

---

### 🔁 Swipe to Refresh

<p align="center">
  <img src="screenshots/swipe_refresh.jpg" width="250"/>
</p>

---

## 🔄 Navigation Flow

```text
            ┌─────────────────────────┐
            │     OnBoardingScreen    │ ◄────────────┐
            └────────────┬────────────┘              │
                         │                           │
            ┌────────────▼────────────┐              │
            │        HomeScreen       │              │
            └────────────┬────────────┘              │
                         │                           │
               ┌─────────▼─────────┐      ┌──────────▼─────────┐
               │ Vertical NewsFeed │◄────▶│    LikedScreen     │
               └───────────────────┘      └────────────────────┘