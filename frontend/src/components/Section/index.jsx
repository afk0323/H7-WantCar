import * as S from './style';

/**
 * Section 구역을 분리하는 컴포넌트
 * @param type {string} TrimSelect || ModelType || ExteriorColor || InteriorColor || AddOption || EstimateFinish
 * @param url {string} InteriorColor, AddOption에 필요한 이미지 URL
 * @param Info {Comment} 'Info' 구역에 안에 넣을 컴포넌트
 * @param Pick {Comment} 'Pick' 구역에 안에 넣을 컴포넌트
 * @returns
 */
function Section({ type, url, Info, Pick }) {
  return (
    <S.Section>
      <S.Background type={type} url={url}>
        <S.Contents>{Info}</S.Contents>
      </S.Background>
      <S.Contents>{Pick}</S.Contents>
    </S.Section>
  );
}

export default Section;
