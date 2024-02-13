// import reset from "styled-reset";
import { createGlobalStyle } from 'styled-components';
import PretendardBlackWoff from './font/Pretendard-Black.woff';
import PretendardBlackWoff2 from './font/Pretendard-Black.woff2';
import PretendardExtraBoldWoff from './font/Pretendard-ExtraBold.woff';
import PretendardExtraBoldWoff2 from './font/Pretendard-ExtraBold.woff2';
import PretendardBoldWoff from './font/Pretendard-Bold.woff';
import PretendardBoldWoff2 from './font/Pretendard-Bold.woff2';
import PretendardSemiBoldWoff from './font/Pretendard-SemiBold.woff';
import PretendardSemiBoldWoff2 from './font/Pretendard-SemiBold.woff2';
import PretendardMediumWoff from './font/Pretendard-Medium.woff';
import PretendardMediumWoff2 from './font/Pretendard-Medium.woff2';
import PretendardRegularWoff from './font/Pretendard-Regular.woff';
import PretendardRegularWoff2 from './font/Pretendard-Regular.woff2';
import PretendardLightWoff from './font/Pretendard-Light.woff';
import PretendardLightWoff2 from './font/Pretendard-Light.woff2';
import PretendardExtraLightWoff from './font/Pretendard-ExtraLight.woff';
import PretendardExtraLightWoff2 from './font/Pretendard-ExtraLight.woff2';
import PretendardThinWoff from './font/Pretendard-Thin.woff';
import PretendardThinWoff2 from './font/Pretendard-Thin.woff2';

const GlobalFont = createGlobalStyle`    
  @font-face {
    font-family: 'Pretendard';
    font-weight: 900;
    font-display: swap;
    src: url(${PretendardBlackWoff2}) format('woff2'), url(${PretendardBlackWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 800;
    font-display: swap;
    src: url(${PretendardExtraBoldWoff2}) format('woff2'), url(${PretendardExtraBoldWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 700;
    font-display: swap;
    src: url(${PretendardBoldWoff2}) format('woff2'), url(${PretendardBoldWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 600;
    font-display: swap;
    src: url(${PretendardSemiBoldWoff2}) format('woff2'), url(${PretendardSemiBoldWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 500;
    font-display: swap;
    src: url(${PretendardMediumWoff2}) format('woff2'), url(${PretendardMediumWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 400;
    font-display: swap;
    src: url(${PretendardRegularWoff2}) format('woff2'), url(${PretendardRegularWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 300;
    font-display: swap;
    src: url(${PretendardLightWoff2}) format('woff2'), url(${PretendardLightWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 200;
    font-display: swap;
    src: url(${PretendardExtraLightWoff2}) format('woff2'), url(${PretendardExtraLightWoff}) format('woff');
  }

  @font-face {
    font-family: 'Pretendard';
    font-weight: 100;
    font-display: swap;
    src: url(${PretendardThinWoff2}) format('woff2'), url(${PretendardThinWoff}) format('woff');
  }

  body {
    margin: 0;
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
      'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
      sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  input {
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
      'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
      sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  button {
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  }

  canvas {
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  }

  tspan {
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  }

  text {
    font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  }
`;

export default GlobalFont;
