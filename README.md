# PangaeaOdysseyWeb

<p align="center">
  <img src="https://raw.githubusercontent.com/DMUGalactic/PangaeaOdysseyWeb/master/assets/logo.png" alt="Pangaea Odyssey Web Logo" width="300" />
</p>

## 💡 개발 계기 및 배경

Pangaea Odyssey Web 프로젝트는 **사용자들이 게임을 단순히 플레이하는 것을 넘어 게임과 관련된 모든 경험을 한 곳에서 통합적으로 관리하고 소통할 수 있는 플랫폼의 필요성**에서 출발하였습니다.

저희 팀은 다음과 같은 목표를 가지고 이 프로젝트를 시작하게 되었습니다:

* **게임과 웹 서비스의 유기적 결합 경험**: 실제 서비스 환경과 유사하게 게임 클라이언트와 웹사이트가 상호작용하는 복합적인 시스템을 구축하여 웹 개발과 게임 개발 역량을 동시에 강화하고자 하였습니다.
* **사용자 중심의 편의성 제공**: 게임 다운로드부터 게시판 활동, 계정 관리까지 모든 과정을 하나의 웹사이트에서 편리하게 이용할 수 있도록 사용자 경험 개선에 중점을 두었습니다.
* **보안 및 효율적인 인증 시스템 학습**: JWT와 Spring Security를 활용하여 안전하고 효율적인 회원가입/로그인 및 권한 관리 시스템을 직접 구현해보고자 하는 계기가 되었습니다.

웹 개발과 게임 개발의 시너지를 탐구하고 실제 서비스 운영에 필요한 다양한 기술적 과제들을 해결하는 귀중한 경험을 얻을 수 있었습니다.

## 👪 개발자 소개
|**강태현**|**서승현**|
|-------|-------|
| 프론트엔드 | 백엔드 |
|[@PoPolar7](https://github.com/PoPolar7)|[@seoseunghyun1025](https://github.com/seoseunghyun1025)|

## 📐 Stack
### Development
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
### Cooperation
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
### Communication
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) ![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white) ![KakaoTalk](https://img.shields.io/badge/kakaotalk-ffcd00.svg?style=for-the-badge&logo=kakaotalk&logoColor=000000)

## 📜 프로젝트 개요
**게임 개발**과 게임을 관리하고 제공하는 **웹사이트 개발**을 목표로 진행된 팀 프로젝트입니다.  
**Pangaea Odyssey Web**은 **게임 다운로드**, **게시판**, **회원가입/로그인** 기능을 포함한 웹사이트입니다.  

## 🧑‍💻 주요 기능
### 1. **회원가입 / 로그인**
- 사용자가 웹사이트에 가입하고 로그인하여 **인증된 사용자**로서 게시판과 게임 다운로드 기능을 이용할 수 있습니다.
- **JWT** 인증 방식을 사용하여 보안성을 높였으며 **Spring Security**로 로그인 세션을 관리합니다.
### 2. **게시판 기능**
- 사용자가 **게시글을 작성**, **수정**, **삭제**할 수 있는 기능을 제공합니다.
- 로그인한 사용자만 자신이 작성한 게시글을 수정하거나 삭제할 수 있도록 **권한 관리**를 추가하였습니다.
### 3. **게임 다운로드**
- 인증된 사용자가 게임 파일을 **다운로드**할 수 있는 기능을 구현하였습니다.
- **파일 업로드 및 다운로드**는 **Spring Boot**와 **MySQL**을 통해 관리됩니다.
### 4. **기타 기능**
- **JWT를 이용한 토큰 관리**: 사용자의 인증 정보를 **JWT 토큰**으로 관리하여 로그인 후 발생하는 요청에서 **토큰 검증**을 통해 사용자의 **인증 상태**를 확인합니다. 서버의 세션 저장소 없이 **효율적인 인증 관리**가 가능하였습니다.

## 🗺️ 시스템 아키텍처
<img src="https://raw.githubusercontent.com/DMUGalactic/PangaeaOdysseyWeb/master/assets/projectarchtecture.png" alt="Pangaea Odyssey Web 시스템 아키텍처 다이어그램" style="max-width:100%; height:auto;">

