import React, { useState } from 'react';
import * as C from './styles';

interface Tab {
  label: string;
  content: React.ReactNode;
}

interface TabsProps {
  tabs: Tab[];
}

const Tabs: React.FC<TabsProps> = ({ tabs }) => {
  const [activeTab, setActiveTab] = useState<number>(0);

  const handleTabClick = (index: number) => {
    setActiveTab(index);
  };

  return (
    <C.TabsContainer>
      <C.TabList>
        {tabs.map((tab, index) => (
          <C.TabButton
            key={index}
            active={index === activeTab}
            onClick={() => handleTabClick(index)}
          >
            {tab.label}
          </C.TabButton>
        ))}
      </C.TabList>
      <C.TabContent>
        {tabs[activeTab]?.content}
      </C.TabContent>
    </C.TabsContainer>
  );
};

export default Tabs;
