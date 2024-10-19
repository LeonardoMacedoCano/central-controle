import { library } from '@fortawesome/fontawesome-svg-core';
import { 
  IconDefinition,
  faGamepad,
  faUser,
  faCog,
  faStar,
  faPlay 
} from '@fortawesome/free-solid-svg-icons';

library.add(faGamepad, faUser, faCog, faStar, faPlay);

const iconMap: Record<string, IconDefinition> = {
  gamepad: faGamepad,
  user: faUser,
  cog: faCog,
  star: faStar,
  play: faPlay,
};

export const getIconByName = (iconName: string): IconDefinition | null => {
  return iconMap[iconName] || null;
};
