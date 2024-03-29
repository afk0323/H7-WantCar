import styled, { keyframes } from 'styled-components';

const backgroundChange = keyframes`
    0%,
    100% {
      background-color: #EDEDEEaa;
    }
    50% {
      background-color: #DADCDDaa;
    }
`;

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
`;

export const MainTitle = styled.div`
  width: 180px;
  height: 24px;
  margin-top: 16px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  animation: ${backgroundChange} 2s infinite;
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: row;
  gap: 16px;
`;

export const MainPick = styled.div`
  width: 240px;
  height: 158px;
  margin-top: 15px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  animation: ${backgroundChange} 2s infinite;
`;
