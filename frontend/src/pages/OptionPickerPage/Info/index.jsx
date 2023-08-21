import { useEffect, useState } from 'react';
import * as S from './style';
import Title from '../../../components/Title';
import Description from './Description';

const TYPE = 'dark';
const CATEGORY = '파워트레인/성능';

function Info({ optionId }) {
  const [optionInfo, setOptionInfo] = useState();
  const TitleProps = {
    type: TYPE,
    subTitle: optionInfo?.category ?? CATEGORY,
    mainTitle: optionInfo?.name,
    info: <Description optionInfo={optionInfo} />,
  };

  useEffect(() => {
    if (!optionId) return;
    fetch(`http://3.36.126.30/models/trims/options/detail?optionId=${optionId}`)
      .then((res) => res.json())
      .then((data) => setOptionInfo(data));
  }, [optionId]);

  if (!optionInfo) return <div>loading...</div>;

  return (
    <S.Info>
      <S.ModelText>
        <Title {...TitleProps} />
      </S.ModelText>
      <S.ModelImage src={optionInfo?.imageUrl} />
    </S.Info>
  );
}

export default Info;
