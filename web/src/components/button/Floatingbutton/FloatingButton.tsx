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

  const enableOptions = () => {
    setShowOptions(true);
  };

  const disableOptions = () => {
    setShowOptions(false);
  };

  const handleOptionClick = (action: () => void) => {
    action();
    disableOptions();
  };

  return (
    <FloatingButtonWrapper>
      <MainButton 
        onMouseEnter={enableOptions} 
        onMouseLeave={disableOptions}
        title={mainButtonHint}
        onClick={mainAction}
        disabled={disabled}
      >
        {mainButtonIcon}
      </MainButton>

      {options && showOptions && (
        <OptionsContainer onMouseEnter={enableOptions} onMouseLeave={disableOptions}>
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
