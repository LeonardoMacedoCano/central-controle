import styled from 'styled-components';

export const FloatingButtonWrapper = styled.div`
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
`;

export const MainButton = styled.button`
  width: 55px;
  height: 55px;
  color: white;
  background-color: ${props => props.theme.colors.tertiary};
  border: none;
  border-radius: 50%;
  font-size: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 1;
  transition: background-color 0.3s ease, opacity 0.3s ease;
  cursor: pointer;

  &:hover {
    opacity: 0.7;
  }
`;

export const OptionsContainer = styled.div`
  position: absolute;
  bottom: 40px;
  right: 0;
  width: 55px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const OptionCircle = styled.button`
  background-color: ${props => props.theme.colors.tertiary};
  color: white;
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 1;
  transition: background-color 0.3s ease, opacity 0.3s ease;
  cursor: pointer;

  &:last-child {
    margin-bottom: 20px;
  }

  &:hover {
    opacity: 0.7;
  }
`;
