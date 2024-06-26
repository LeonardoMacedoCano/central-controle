import React, { useState } from 'react';
import { FloatingButtonWrapper, MainButton, OptionCircle, OptionsContainer } from './styles';

interface Option {
  icon: React.ReactNode;
  hint: string;
  action: () => void;
}

interface FloatingButtonProps {
  mainButtonIcon: React.ReactNode;
  mainButtonHint?: string;
  mainAction?: () => void;
  options?: Option[];
  disabled?: boolean;
}

const FloatingButton: React.FC<FloatingButtonProps> = ({ mainButtonIcon, mainButtonHint, mainAction, options, disabled }) => {
  const [showOptions, setShowOptions] = useState<boolean>(false);

  const toggleOptions = (show: boolean) => {
    setShowOptions(show);
  };

  const handleOptionClick = (action: () => void) => {
    action();
    toggleOptions(false);
  };

  return (
    <FloatingButtonWrapper>
      <MainButton 
        onMouseEnter={() => toggleOptions(true)} 
        onMouseLeave={() => toggleOptions(false)}
        title={mainButtonHint}
        onClick={mainAction}
        disabled={disabled}
      >
        {mainButtonIcon}
      </MainButton>

      {options && showOptions && (
        <OptionsContainer onMouseEnter={() => toggleOptions(true)} onMouseLeave={() => toggleOptions(false)}>
          {options.map((option, index) => (
            <OptionCircle key={index} onClick={() => handleOptionClick(option.action)} title={option.hint}>
              {option.icon}
            </OptionCircle>
          ))}
        </OptionsContainer>
      )}
    </FloatingButtonWrapper>
  );
};

export default FloatingButton;
