import styled from 'styled-components';

export const TabsContainer = styled.div`
  width: 100%;
  height: auto;
`;

export const TabList = styled.div`
  display: flex;
  border-bottom: 2px solid ${({ theme }) => theme.colors.quaternary};
`;

export const TabButton = styled.button<{ active: boolean }>`
  flex: 1;
  padding: 10px 20px;
  border: none;
  color: ${({ theme }) => theme.colors.white};
  background-color: ${({ theme }) => theme.colors.secondary};
  cursor: pointer;
  transition: background-color 0.3s, border-right-color 0.3s;

  &:not(:last-child) {
    border-right: 2px solid ${({ theme }) => theme.colors.tertiary};
  }

  &:first-child {
    border-top-left-radius: 5px;
  }

  &:last-child {
    border-top-right-radius: 5px;
  }

  &:hover {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }

  ${({ active, theme }) =>
    active &&
    `
    cursor: default;
    background-color: ${theme.colors.tertiary};
    border-right-color: transparent;
  `}
`;

export const TabContent = styled.div`
  padding: 20px 10px;
`;
